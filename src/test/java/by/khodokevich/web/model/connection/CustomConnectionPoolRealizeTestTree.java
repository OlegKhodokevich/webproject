package by.khodokevich.web.model.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomConnectionPoolRealizeTestTree {
    private static final Logger logger = LogManager.getLogger(CustomConnectionPoolGetInstanceTest.class);
    CustomConnectionPool customConnectionPool;

    @BeforeClass
    public void beforeClass() {
        customConnectionPool = CustomConnectionPool.getInstance();
    }

    @AfterClass
    public void afterClass() throws PoolConnectionException {
    }

    @Test(groups = "pool_connection", priority = 4)
    public void testGetInstance2() throws PoolConnectionException {
        logger.debug("Start testGetInstance2();");
        List<ProxyConnection> connections = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            connections.add(customConnectionPool.getConnection());
        }
        for (int i = 0; i < 8; i++) {
            customConnectionPool.releaseConnection(connections.get(i));
        }
        Assert.assertTrue(customConnectionPool.isFull());

    }
}
