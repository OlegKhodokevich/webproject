package by.khodokevich.web.connection;

import by.khodokevich.web.exception.PoolConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    private static final String FILE_PROPERTIES_NAME = "database";
    private static final String URL_ATTRIBUTE_NAME = "db.url";
    private static final String USER_ATTRIBUTE_NAME = "db.user";
    private static final String PASSWORD_ATTRIBUTE_NAME = "db.password";
    private static final String DRIVER_ATTRIBUTE_NAME = "db.driver";

    private static String driverName;
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(FILE_PROPERTIES_NAME, new Locale("en", "US"));

            url = resourceBundle.getString(URL_ATTRIBUTE_NAME);
            user = resourceBundle.getString(USER_ATTRIBUTE_NAME);
            password = resourceBundle.getString(PASSWORD_ATTRIBUTE_NAME);
            driverName = resourceBundle.getString(DRIVER_ATTRIBUTE_NAME);
            Class.forName(driverName);
            logger.info("Driver has been loaded : " + driverName);
        } catch (MissingResourceException e) {
            logger.fatal("Resource bundle can't be found for the specified base name  = " + FILE_PROPERTIES_NAME + " . Exception = " + e.getMessage());
            throw new ExceptionInInitializerError("Resource bundle can't be found for the specified base name  = " + FILE_PROPERTIES_NAME + " . Exception = " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver can't be set. Driver = " + driverName + " . Exception = " + e.getMessage());
            throw new ExceptionInInitializerError("Driver can't be set. Driver = " + driverName + " . Exception = " + e.getMessage());
        }
    }

    static ProxyConnection createConnection() throws PoolConnectionException {
        ProxyConnection connection = null;
        try {
            connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            throw new PoolConnectionException("Connection can't be created." + e.getMessage());
        }
        return connection;
    }
}
