package by.khodokevich.web.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RegexpManager {
    private static final Logger logger = LogManager.getLogger(RegexpManager.class);
    private static final ResourceBundle resourceBundle;
    private static final String FILE_PROPERTIES_NAME = "regexp";

    static {
        try {
            resourceBundle = ResourceBundle.getBundle(FILE_PROPERTIES_NAME);
        } catch (MissingResourceException e) {
            logger.error("Resource bundle can't be found for the specified base name  = " + FILE_PROPERTIES_NAME + " . Exception = " + e.getMessage());
            throw new ExceptionInInitializerError("Resource bundle can't be found for the specified base name  = " + FILE_PROPERTIES_NAME + " . Exception = " + e.getMessage());
        }
    }

    public static String getRegexp(String key) {
        return resourceBundle.getString(key);
    }

}
