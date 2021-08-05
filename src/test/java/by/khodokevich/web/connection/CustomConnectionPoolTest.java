package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class CustomConnectionPoolTest {
    private  static final Logger logger = LogManager.getLogger(CustomConnectionPoolTest.class);
    CustomConnectionPool customConnectionPool;


    @BeforeMethod
    public void setUp() {
        logger.info("Start before method.");
        customConnectionPool = CustomConnectionPool.getInstance();
        logger.info("Pool = " + customConnectionPool.toString());
    }

    @AfterMethod
    public void tearDown() throws PoolConnectionException {

        logger.info("Start after method.");
        customConnectionPool.destroyPool();
        customConnectionPool = null;
    }


    @Test(groups = "pool_connection", priority = 2)
    public void testIsFull1() throws PoolConnectionException {
        logger.debug("Start testIsFull1().");
        boolean result = customConnectionPool.isFull();

        Assert.assertTrue(result);
    }


    @Test(groups = "pool_connection", priority = 2)
    public void testIsFull2() throws PoolConnectionException {
        logger.debug("Start testIsFull2().");
        ProxyConnection connection = customConnectionPool.getConnection();
        connection.reallyClose();
        customConnectionPool.releaseConnection(connection);
        boolean result = CustomConnectionPool.getInstance().isFull();

        Assert.assertFalse(result);
    }
}