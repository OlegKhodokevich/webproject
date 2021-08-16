package by.khodokevich.web.model.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomConnectionPoolGetInstanceTest {
private static final Logger logger = LogManager.getLogger(CustomConnectionPoolGetInstanceTest.class);
    CustomConnectionPool customConnectionPool;

    @BeforeClass
    public void beforeClass() {
        logger.debug("Before class");
        customConnectionPool = CustomConnectionPool.getInstance();
    }

    @AfterClass
    public void afterClass() throws PoolConnectionException {
    }

    @Test(groups = "pool_connection", priority = 1)
    public void testGetInstance1() throws PoolConnectionException, SQLException, InterruptedException {
        logger.debug("Start testGetInstance1();");
        ProxyConnection proxyConnection = customConnectionPool.getConnection();
        Assert.assertNotNull(proxyConnection);
        customConnectionPool.releaseConnection(proxyConnection);
    }

    @Test(expectedExceptions = PoolConnectionException.class, groups = "pool_connection", priority = 2)
    public void testGetInstance2() throws PoolConnectionException {
        logger.debug("Start testGetInstance2();");
        List<ProxyConnection> connections = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            connections.add(customConnectionPool.getConnection());
        }
        logger.debug("Place there");
        ProxyConnection proxyConnection = customConnectionPool.getConnection();
        logger.debug("after get connection");
        for (int i = 0; i < 8; i++) {
            customConnectionPool.releaseConnection(connections.get(i));
        }
        Assert.assertNull(proxyConnection);

    }
}