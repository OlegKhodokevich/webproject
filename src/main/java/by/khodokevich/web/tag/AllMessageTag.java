package by.khodokevich.web.tag;

import by.khodokevich.web.command.ParameterAttributeType;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class AllMessageTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(AllMessageTag.class);
    private ResourceBundle resourceBundle;
    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private static final String REGEXP_LANGUAGE = "\\p{Alpha}{2}";
    private static final String FILE_RESOURCE_NAME = "text";

    public void setKeyMessage() {
    }

    @Override
    public int doStartTag() {
        logger.debug("Start doStartTag(). message = ");
        StringBuffer stringBuffer = new StringBuffer();

        HttpSession session = pageContext.getSession();
        String keyMessage = (String) session.getAttribute("message");
        if (keyMessage != null) {
            String localeString = (String) session.getAttribute(ParameterAttributeType.LOCALE);
            if (localeString != null) {
                Locale currentLocal = parseString(localeString);
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, currentLocal);
            } else {
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, DEFAULT_LOCALE);
            }
            try {
                String message = resourceBundle.getString(keyMessage);
                pageContext.getOut().write("<p>" + message + "<p/>");
                session.removeAttribute(ParameterAttributeType.MESSAGE);
            } catch (IOException e) {
                logger.error("Can't write the keyMessage : " + stringBuffer);
            } catch (MissingResourceException e) {
                logger.error("Wrong property file or localeString");
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private Locale parseString(String localString) {
        Locale locale;
        Pattern pattern = Pattern.compile(REGEXP_LANGUAGE);
        Matcher matcher = pattern.matcher(localString);
        String language;
        String country;
        if (matcher.find()) {
            language = matcher.group();
            if (matcher.find()) {
                country = matcher.group();
                locale = new Locale(language, country);
            } else {
                locale = DEFAULT_LOCALE;
            }
        } else {
            locale = DEFAULT_LOCALE;
        }
        return locale;
    }
}
