package by.khodokevich.web.connection;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.PoolConnectionException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.testng.Assert.*;

import static org.mockito.Mockito.when;
public class ConnectionFactoryTest {

    @Test(groups = "pool_connection", priority = 0)
    public void testCreateConnection1() throws PoolConnectionException {
        ProxyConnection proxyConnection = ConnectionFactory.createConnection();
        Assert.assertNotNull(proxyConnection);
        proxyConnection.reallyClose();
    }
}