package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class CustomConnectionPoolGetInstanceTest {
private static final Logger logger = LogManager.getLogger(CustomConnectionPoolGetInstanceTest.class);
    CustomConnectionPool customConnectionPool;

    @BeforeMethod
    public void setUp() {
        customConnectionPool = CustomConnectionPool.getInstance();
    }

    @AfterMethod
    public void tearDown() throws PoolConnectionException {
        customConnectionPool.destroyPool();
//        customConnectionPool = null;
    }

    @Test(groups = "pool_connection1", priority = 1)
    public void testGetInstance1() throws PoolConnectionException, SQLException, InterruptedException {
        logger.debug("Start testGetInstance1();");
        ProxyConnection proxyConnection = customConnectionPool.getConnection();
        Assert.assertNotNull(proxyConnection);
        proxyConnection.reallyClose();
        Thread.currentThread().join(4);
    }

    @Test(expectedExceptions = PoolConnectionException.class, groups = "pool_connection1", priority = 2)
    public void testGetInstance2() throws PoolConnectionException, SQLException {
        logger.debug("Start testGetInstance2();");
        List<ProxyConnection> connections = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            connections.add(customConnectionPool.getConnection());
        }
        ProxyConnection proxyConnection = customConnectionPool.getConnection();
        Assert.assertNull(proxyConnection);

    }
}