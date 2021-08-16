package by.khodokevich.web.model.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class CustomConnectionPoolIsFullTestOne {
    private  static final Logger logger = LogManager.getLogger(CustomConnectionPoolIsFullTestOne.class);
    CustomConnectionPool customConnectionPool;


    @BeforeMethod
    public void setUp() {
        logger.info("Start before method.");
        customConnectionPool = CustomConnectionPool.getInstance();
        logger.info("Pool = " + customConnectionPool.toString());
    }

    @Test(groups = "pool_connection", priority = 2)
    public void testIsFull1() throws PoolConnectionException {
        logger.debug("Start testIsFull1().");
        boolean result = customConnectionPool.isFull();

        Assert.assertTrue(result);
    }
}