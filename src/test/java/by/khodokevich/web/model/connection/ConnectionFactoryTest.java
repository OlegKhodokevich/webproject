package by.khodokevich.web.model.connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class ConnectionFactoryTest {

    @Test(groups = "pool_connection", priority = 0)
    public void testCreateConnection1() throws SQLException {
        ProxyConnection proxyConnection = ConnectionFactory.createConnection();
        Assert.assertNotNull(proxyConnection);
    }
}