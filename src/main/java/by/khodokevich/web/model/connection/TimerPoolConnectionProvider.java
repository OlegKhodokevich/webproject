package by.khodokevich.web.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TimerConnectionProvider is timer task which chek pool and add connection if it isn't full.
 * TimerConnectionProvider stops operation in pool during it work.
 *
 * @author Oleg Khodokevich
 *
 */
public class TimerPoolConnectionProvider extends TimerTask {
    private static final Logger logger = LogManager.getLogger(TimerPoolConnectionProvider.class);

    private static final int TIME_FOR_WAIT_BEFORE_CONNECTION_PROVIDER_WORK_MICROSECONDS = 100;
    private static final AtomicBoolean isConnectionProviderRun = CustomConnectionPool.isConnectionProviderRun;
    private static final ReentrantLock timerConnectionProviderLock = CustomConnectionPool.timerConnectionProviderLock;
    private static Condition condition = timerConnectionProviderLock.newCondition();

    @Override
    public void run() {
        logger.info("Start TimerConnectionProvider.");
        try {
            try {
                timerConnectionProviderLock.lock();
                condition.await(TIME_FOR_WAIT_BEFORE_CONNECTION_PROVIDER_WORK_MICROSECONDS, TimeUnit.MICROSECONDS);
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
