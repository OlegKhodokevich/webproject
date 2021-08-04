package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WebMain {
    private static final Logger logger = LogManager.getLogger();

    private final static long CHECK_TIME_MILLISECONDS = 3_600_000;
    private static final ReentrantLock locker = new ReentrantLock();
    private static final Condition condition = locker.newCondition();

    public static void main(String[] args) throws SQLException, PoolConnectionException {

        CustomConnectionPool customConnectionPool = CustomConnectionPool.getInstance();
        Timer timer = new Timer();
        timer.schedule(new TimerConnectionProvider(), 2, 50);
        for (int i = 0; i < 35; i++) {
            ProxyConnection connection = customConnectionPool.getConnection();
            try {
                locker.lock();
                System.out.println(" Get connection = " + connection.getCatalog() + " " +i);
                condition.await(10, TimeUnit.MILLISECONDS);
                if (i %4 == 0 && !connection.isClosed()) {

                    connection.reallyClose();
                }

                connection.close();
                System.out.println(" Put connection = " + connection.getCatalog() + " " +i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                locker.unlock();
            }

        }
//        connection.reallyClose();
//        connection.isClosed();
//        connection.close();
        try {
            locker.lock();

            condition.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            locker.unlock();
        }
        System.out.println("STOP   STOP   STOP   STOP   STOP   STOP   STOP   STOP   STOP   STOP   ");
        timer.cancel();
//        customConnectionPool.isFull();
        try {
            locker.lock();

            condition.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            locker.unlock();
        }


        customConnectionPool.destroyPool();
    }
}
