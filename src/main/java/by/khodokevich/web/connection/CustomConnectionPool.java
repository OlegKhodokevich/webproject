package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConnectionPool {
    private static final Logger logger = LogManager.getLogger(CustomConnectionPool.class);
    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int MAX_NUMBER_ADDITIONAL_CONNECTION_ATTEMPT = 3;
    private static final int MAX_WAITING_TIME_GET_CONNECTION_SECONDS = 5;
    private static final int TIME_FOR_WAIT_WHEN_CONNECTION_PROVIDER_WORK_MICROSECONDS = 500;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private static CustomConnectionPool instance;

    protected static final AtomicBoolean isConnectionProviderRun = new AtomicBoolean(false);
    protected static ReentrantLock timerConnectionProviderLock = new ReentrantLock();
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
            if (timerConnectionProviderLock.tryLock(MAX_WAITING_TIME_GET_CONNECTION_SECONDS, TimeUnit.SECONDS)) {
                connection = freeConnections.take();
                busyConnections.put(connection);
                logger.info("Connection has given.");
                return connection;
            }
        } catch (InterruptedException e) {
            logger.error("Cannot take or put connection" + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            timerConnectionProviderLock.unlock();
        }
        throw new PoolConnectionException("Time out for getting connection.");
    }

    public void releaseConnection(Connection connection) {
        logger.debug("Start releaseConnection(Connection connection).");
        stopGiveTakeConnectionWhenConnectionProviderRun();
        if (connection instanceof ProxyConnection) {
            try {
                if (busyConnections.remove(connection)) {
                    freeConnections.put((ProxyConnection) connection);
                } else {
                    logger.error("Can't put in pool connection because connection is't valid.");
                }
            } catch (InterruptedException e) {
                logger.error("Cannot put connection" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else {
            logger.error("Find wild connection.");
        }
        logger.info("Connection realize.");
    }

    public void destroyPool() {
        logger.error("Start destroyPool(). freeConnections.size() + " + freeConnections.size());
        stopGiveTakeConnectionWhenConnectionProviderRun();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Cannot take connection" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
        logger.error("End destroyPool().");
    }

    boolean isFull() {
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
                ProxyConnection connection = freeConnections.take();
                if (connection.isValid(1)) {
                    temptConnections.put(connection);
                }
            } catch (InterruptedException e) {
                logger.error("Cannot take or put connection" + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (SQLException e) {
                logger.error("Can't check connection." + e.getMessage());
            }
        }
        int temptConnectionsSize = temptConnections.size();
        for (int i = 0; i < temptConnectionsSize; i++) {
            try {
                ProxyConnection connection = temptConnections.take();
                freeConnections.put(connection);
            } catch (InterruptedException e) {
                logger.error("Cannot put connection" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        connectionPoolSize = (freeConnections.size() + busyConnections.size());
        logger.info("End isFull(). Number connections in pool = " + connectionPoolSize);
        result = connectionPoolSize == CustomConnectionPool.DEFAULT_POOL_SIZE;

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
                freeConnections.put(proxyConnection);
                connectionPoolSize = (freeConnections.size() + busyConnections.size());
            } catch (InterruptedException e) {
                logger.error("Cannot put connection" + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (PoolConnectionException e) {
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

}
