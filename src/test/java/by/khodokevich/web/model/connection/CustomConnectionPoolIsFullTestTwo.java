package by.khodokevich.web.model.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class CustomConnectionPoolIsFullTestTwo {
    private  static final Logger logger = LogManager.getLogger(CustomConnectionPoolIsFullTestTwo.class);
    CustomConnectionPool customConnectionPool;

    @BeforeMethod
    public void setUp() {
        logger.info("Start before method.");
        customConnectionPool = CustomConnectionPool.getInstance();
        logger.info("Pool = " + customConnectionPool.toString());
    }

    @Test(groups = "pool_connection", priority = 3)
    public void testIsFull2() throws PoolConnectionException {
        logger.debug("Start testIsFull2().");
        ProxyConnection connection = customConnectionPool.getConnection();
        connection.reallyClose();
        customConnectionPool.releaseConnection(connection);
        boolean result = CustomConnectionPool.getInstance().isFull();

        Assert.assertFalse(result);
    }
}