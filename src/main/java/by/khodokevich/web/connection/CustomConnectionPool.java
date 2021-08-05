package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger(CustomConnectionPool.class);
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int MAX_NUMBER_ADDITIONAL_CONNECTION_ATTEMPT = 3;
//    private static final int MAX_WAITING_TIME_GET_CONNECTION_SECONDS = 5;
    private static final int TIME_FOR_WAIT_WHEN_CONNECTION_PROVIDER_WORK_MICROSECONDS = 500;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private static CustomConnectionPool instance;

    protected static final AtomicBoolean isConnectionProviderRun = new AtomicBoolean(false);
    protected static ReentrantLock timerConnectionProviderLock = new ReentrantLock();
    protected static Semaphore semaphore = new Semaphore(DEFAULT_POOL_SIZE);
    private static Condition timerConnectionProviderLockCondition = timerConnectionProviderLock.newCondition();

    private BlockingDeque<ProxyConnection> freeConnections;
    private BlockingDeque<ProxyConnection> busyConnections;


    private CustomConnectionPool() {
        freeConnections = new LinkedBlockingDeque(DEFAULT_POOL_SIZE);
        busyConnections = new LinkedBlockingDeque(DEFAULT_POOL_SIZE);

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            ProxyConnection connection = null;
            try {
                connection = ConnectionFactory.createConnection();
            } catch (PoolConnectionException e) {
                logger.info("Connection can't be created.");
            }
            freeConnections.offer(connection);
        }
        if (freeConnections.isEmpty()) {
            logger.fatal("Connections pool can't be created.");
            throw new RuntimeException("Connections pool can't be created.");
        } else if (freeConnections.size() == DEFAULT_POOL_SIZE) {
            logger.info("Pool connection was created successfully. Size = " + freeConnections.size());
        } else {
            logger.info("Pool connection isn't full. Size = " + freeConnections.size());
            addConnectionsToPool();
        }
    }

    public static CustomConnectionPool getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndExchange(false, true)) {
                instance = new CustomConnectionPool();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() throws PoolConnectionException {
        logger.info("Start getConnection(). freeConnections.size() = " + freeConnections.size() + "   busyConnections.size() " + busyConnections.size());
        ProxyConnection connection = null;
        stopGiveTakeConnectionWhenConnectionProviderRun();
        try {
            boolean hasNext = semaphore.tryAcquire();
            if (hasNext) {
                logger.debug("After" + semaphore.availablePermits());
                connection = freeConnections.remove();
                busyConnections.offer(connection);
                logger.info("Connection has given.");
                return connection;
            }
        } catch (NoSuchElementException e) {
            logger.error("Cannot remove connection" + e.getMessage());
            throw new PoolConnectionException("Cannot remove connection", e);
        }
        throw new PoolConnectionException("Time out for getting connection.");
    }

    public void releaseConnection(Connection connection) {
        logger.debug("Start releaseConnection(Connection connection).");
        stopGiveTakeConnectionWhenConnectionProviderRun();
        if (connection instanceof ProxyConnection proxyConnection) {
            if (busyConnections.remove(proxyConnection)) {
                freeConnections.offer(proxyConnection);
                semaphore.release();
            } else {
                logger.error("Can't put in pool connection because connection is't valid.");
            }
        } else {
            logger.error("Find wild connection.");
        }
        logger.info("Connection realize.");
    }

    public void destroyPool() throws PoolConnectionException {
        logger.info("Start destroyPool(). freeConnections.size(). Size free = " + freeConnections.size() + " Size busy = " + busyConnections.size());
        stopGiveTakeConnectionWhenConnectionProviderRun();

        int sizeBusyConnectionList = busyConnections.size();
        for (int i = 0; i < sizeBusyConnectionList; i++) {
            try {
                ProxyConnection proxyConnection = busyConnections.remove();
                freeConnections.offer(proxyConnection);
                semaphore.release();

                logger.debug("busy = " + busyConnections.size());
                semaphore.release();
                logger.debug("busy = " + busyConnections.size());
            }catch (NoSuchElementException e) {
                logger.error("Cannot remove connection" + e.getMessage());
                throw new PoolConnectionException("Cannot remove connection", e);
            }

        }
        logger.debug("free before = " + freeConnections.size());
        int sizeFreeConnectionList = freeConnections.size();
        for (int i = 0; i < sizeFreeConnectionList; i++) {
            logger.debug("i = " + i);
            logger.debug("free = " + freeConnections.size());
            try {
                freeConnections.remove().reallyClose();
                semaphore.release();
                logger.debug("free = " + freeConnections.size());
            }catch (NoSuchElementException e) {
                logger.error("Cannot remove connection" + e.getMessage());
                throw new PoolConnectionException("Cannot remove connection", e);
            }

        }
        deregisterDrivers();
        isInitialized.set(false);
        instance = null;
        logger.info("End destroyPool(). Size free = " + freeConnections.size());
    }

    boolean isFull() throws PoolConnectionException {
        boolean result;
        logger.info("Start check pool isFull().");
        BlockingDeque<ProxyConnection> temptConnections = new LinkedBlockingDeque<>();
        int connectionPoolSize;
        connectionPoolSize = (freeConnections.size() + busyConnections.size());
        if (connectionPoolSize == 0) {
            logger.fatal("Connections pool is empty.");
            throw new RuntimeException("Connections pool is empty.");
        }
        logger.info("Number connections in pool = " + connectionPoolSize);
        int freeConnectionsSize = freeConnections.size();
        for (int i = 0; i < freeConnectionsSize; i++) {
            try {
                ProxyConnection connection = freeConnections.remove();
                if (connection.isValid(1)) {
                    temptConnections.offer(connection);
                } else {
                    logger.error("Has found bad connection = ");
                }
            } catch (NoSuchElementException e) {
                logger.error("Cannot remove connection" + e.getMessage());
                throw new PoolConnectionException("Cannot remove connection", e);
            } catch (SQLException e) {
                logger.error("Can't check connection." + e.getMessage());
            }
        }
        int temptConnectionsSize = temptConnections.size();
        for (int i = 0; i < temptConnectionsSize; i++) {
            try {
                ProxyConnection connection = temptConnections.remove();
                freeConnections.offer(connection);
            } catch (NoSuchElementException e) {
                logger.error("Cannot remove connection" + e.getMessage());
                throw new PoolConnectionException("Cannot remove connection", e);
            }
        }
        connectionPoolSize = (freeConnections.size() + busyConnections.size());
        logger.info("End isFull(). Number connections in pool = " + connectionPoolSize);
        result = connectionPoolSize == DEFAULT_POOL_SIZE;
        semaphore.release(DEFAULT_POOL_SIZE - connectionPoolSize);

        return result;
    }

    void addConnectionsToPool() {
        int count = 0;
        int connectionPoolSize;

        connectionPoolSize = (freeConnections.size() + busyConnections.size());
        logger.info("Start addConnectionsToPool(). Number connections in pool = " + connectionPoolSize);

        while (connectionPoolSize < DEFAULT_POOL_SIZE & count < MAX_NUMBER_ADDITIONAL_CONNECTION_ATTEMPT) {
            try {
                Connection connection = ConnectionFactory.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
                connectionPoolSize = (freeConnections.size() + busyConnections.size());
            }  catch (PoolConnectionException e) {
                logger.info("Connection can't be created.");
            }
            count++;
        }

        if (count == MAX_NUMBER_ADDITIONAL_CONNECTION_ATTEMPT && connectionPoolSize < DEFAULT_POOL_SIZE) {
            logger.fatal("Connections pool can't be fill. Current size = " + connectionPoolSize);
            throw new RuntimeException("Connections pool can't be fill. Current size = " + connectionPoolSize);
        }
        logger.info("Number addition attempts = " + count);
        logger.info("End addConnectionsToPool(). Number connections in pool = " + connectionPoolSize);
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.fatal("Driver can't be deregister. " + e.getMessage());
                throw new RuntimeException("Driver can't be deregister. " + e.getMessage());
            }
        });
    }

    private void stopGiveTakeConnectionWhenConnectionProviderRun() {
        while (isConnectionProviderRun.get()) {
            try {
                timerConnectionProviderLock.lock();
                logger.info("Work Connection Provider. Please wait.");
                timerConnectionProviderLockCondition.await(TIME_FOR_WAIT_WHEN_CONNECTION_PROVIDER_WORK_MICROSECONDS, TimeUnit.MICROSECONDS);
            } catch (InterruptedException e) {
                logger.error("Thread has been interrupt", e);
                Thread.currentThread().interrupt();
            } finally {
                timerConnectionProviderLock.unlock();
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomConnectionPool{");
        sb.append("freeConnections.size=").append(freeConnections.size());
        sb.append(", busyConnections.size=").append(busyConnections.size());
        sb.append('}');
        return sb.toString();
    }
}
