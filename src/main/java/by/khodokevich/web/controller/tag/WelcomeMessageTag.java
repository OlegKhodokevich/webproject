package by.khodokevich.web.controller.tag;

import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
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

import static by.khodokevich.web.controller.command.InformationMessage.*;


/**
 * Tag which only shows first message after sign in for greetings.
 *
 * @author Oleg Khodokevich
 *
 */
public class WelcomeMessageTag extends TagSupport {
    public static final boolean isFirst = true;
    private static final Logger logger = LogManager.getLogger(WelcomeMessageTag.class);
    private static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");
    private static final String REGEXP_LANGUAGE = "\\p{Alpha}{2}";
    private static final String FILE_RESOURCE_NAME = "text";


    @Override
    public int doStartTag() throws JspException {
        logger.debug("Start doStartTag().");
        StringBuilder stringBuilder = new StringBuilder();
        HttpSession session = pageContext.getSession();
        User activeUser = (User) session.getAttribute("activeUser");
        String isFirstCustomTag = (String) session.getAttribute("isFirstCustomTag");
        if (isFirstCustomTag == null && activeUser != null) {
            String firstName = activeUser.getFirstName();
            String lastName = activeUser.getLastName();
            UserRole role = activeUser.getRole();
            String localeString = (String) session.getAttribute(ParameterAttributeType.LOCALE);
            ResourceBundle resourceBundle;
            if (localeString != null) {
                Locale currentLocal = parseString(localeString);
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, currentLocal);
            } else {
                resourceBundle = ResourceBundle.getBundle(FILE_RESOURCE_NAME, DEFAULT_LOCALE);
            }
            try {
                if (role == UserRole.ADMIN) {
                    stringBuilder.append(firstName).append(" ")
                            .append(lastName).append(" ")
                            .append(resourceBundle.getString(WELCOME_ADMIN_MESSAGE));
                } else {
                    stringBuilder.append(firstName).append(" ")
                            .append(lastName).append(" ")
                            .append(resourceBundle.getString(WELCOME_USER_MESSAGE));
                }
                logger.debug("message " + stringBuilder);
                pageContext.getOut().write("<h1>" + stringBuilder + "<h1>");

                session.setAttribute("isFirstCustomTag", "false");
            } catch (IOException e) {
                logger.error("Can't write the message : " + stringBuilder);
                throw new JspException(e.getMessage());
            } catch (MissingResourceException e) {
                logger.error("Wrong property file or localeString");
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
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
