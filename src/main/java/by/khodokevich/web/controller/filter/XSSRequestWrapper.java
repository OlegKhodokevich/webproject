package by.khodokevich.web.controller.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class wraps request and override methods which get parameter or header.
 * It is used for XSSFilter.
 *
 * @author Oleg Khodokevich
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger logger = LogManager.getLogger(XSSRequestWrapper.class);

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        String[] encodedValues = null;
        if (values != null) {
            int length = values.length;
            encodedValues = new String[length];
            for (int i = 0; i < length; i++) {
                encodedValues[i] = stripXSS(values[i]);
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        String encodedValue = stripXSS(value);

        logger.debug("Value = " + value + " , encodedValue  =" + encodedValue);
        return encodedValue;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    private String stripXSS(String value) {
        if (value != null){
            value = value.replace("<","");
            value = value.replace(">","");
        }
        return value;
    }
}
