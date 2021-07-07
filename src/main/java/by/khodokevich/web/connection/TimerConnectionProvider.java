package by.khodokevich.web.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TimerConnectionProvider extends TimerTask {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isConnectionProviderRun = CustomConnectionPool.isConnectionProviderRun;
    private static final ReentrantLock timerConnectionProviderLock = CustomConnectionPool.timerConnectionProviderLock;
    private static Condition condition = timerConnectionProviderLock.newCondition();

    @Override
    public void run() {
        try {
            try {
                timerConnectionProviderLock.lock();
                condition.await(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("Can't wait.", e);
                Thread.currentThread().interrupt();
            } finally {
                timerConnectionProviderLock.unlock();
            }
            isConnectionProviderRun.set(true);

            logger.info("TimerConnectionProvider start check pool.");
            CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();

            if (!connectionPool.isFull()) {
                connectionPool.addConnectionsToPool();
            }
        } finally {
            isConnectionProviderRun.set(false);
        }
        logger.info("TimerConnectionProvider has checked pool.");
    }


}
