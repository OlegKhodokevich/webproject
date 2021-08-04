package by.khodokevich.web.tag;

import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
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
public class WelcomeMessageTag extends TagSupport {
    public static final boolean isFirst = true;
    private static final Logger logger = LogManager.getLogger(WelcomeMessageTag.class);
    private ResourceBundle resourceBundle;
    private static final String WELCOME_USER_MESSAGE = "logging.welcome_user";
    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private static final String WELCOME_ADMIN_MESSAGE = "logging.welcome_admin";
    private static final String REGEXP_LANGUAGE = "\\p{Alpha}{2}";

    private static final String FILE_RESOURCE_NAME = "text";


    @Override
    public int doStartTag() {
        logger.debug("Start doStartTag().");
        StringBuffer stringBuffer = new StringBuffer();

        HttpSession session = pageContext.getSession();
        User activeUser = (User) session.getAttribute("activeUser");
        String isFirstCustomTag = (String) session.getAttribute("isFirstCustomTag");
        if (isFirstCustomTag == null && activeUser != null) {
            String firstName = activeUser.getFirstName();
            String lastName = activeUser.getLastName();
            UserRole role = activeUser.getRole();
            String localeString = (String) session.getAttribute(ParameterAttributeType.LOCALE);
            if (localeString != null) {
                Locale currentLocal = parseString(localeString);
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, currentLocal);
            } else {
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, DEFAULT_LOCALE);
            }
            try {
                if (role == UserRole.ADMIN) {
                    stringBuffer.append(firstName).append(" ")
                            .append(lastName).append(" ")
                            .append(resourceBundle.getString(WELCOME_ADMIN_MESSAGE));
                } else {
                    stringBuffer.append(firstName).append(" ")
                            .append(lastName).append(" ")
                            .append(resourceBundle.getString(WELCOME_USER_MESSAGE));
                }
                logger.debug("message " + stringBuffer);
                pageContext.getOut().write("<h1>" + stringBuffer + "<h1>");

                session.setAttribute("isFirstCustomTag", "false");
            } catch (IOException e) {
                logger.error("Can't write the message : " + stringBuffer);
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
