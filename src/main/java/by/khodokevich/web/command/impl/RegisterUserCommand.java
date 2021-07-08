package by.khodokevich.web.command.impl;

import static by.khodokevich.web.command.ParameterAndAttributeType.*;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.service.CheckingResultType;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;
import com.oracle.wls.shaded.org.apache.regexp.RE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String KEY_SENT_MASSAGE = "registration.message_send_link";
    private static final String KEY_MESSAGE_DATA_NOT_CORRECT = "registration.message_data_not_correct";
    private static final String KEY_MESSAGE_DUPLICATE_EMAIL = "registration.message_duplicate_email";
    private static final String KEY_MESSAGE_DUPLICATE_PHONE = "registration.message_duplicate_phone";
    private static final String KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE = "registration.message_duplicate_email_and_phone";
    private static final String EMPTY_VALUE = "";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String eMail = request.getParameter(E_MAIL);
        String phone = request.getParameter(PHONE);
        String region = request.getParameter(REGION);
        String city = request.getParameter(CITY);
        String password = request.getParameter(PASSWORD);
        Map<String, String> userData = new HashMap<>();
        userData.put(FIRST_NAME, firstName);
        userData.put(LAST_NAME, lastName);
        userData.put(E_MAIL, eMail);
        userData.put(PHONE, phone);
        userData.put(REGION, region);
        userData.put(CITY, city);
        userData.put(PASSWORD, password);
        userData.put(REPEATED_PASSWORD, password);
        userData.put(URL, request.getRequestURL().toString());
        UserService userService = new UserServiceImpl();
        Map<String, String> answerMap;
        CheckingResultType resultType;
        try {
            answerMap = userService.register(userData);
            String result = answerMap.get(RESULT);
            if (result != null) {
                resultType = CheckingResultType.valueOf(result);
                switch (resultType) {
                    case SUCCESS:
                        router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                        request.setAttribute(MESSAGE, KEY_SENT_MASSAGE);
                        break;
                    case NOT_VALID:
                        request.setAttribute(MESSAGE, KEY_MESSAGE_DATA_NOT_CORRECT);
                        firstName = answerMap.get(FIRST_NAME);
                        lastName = answerMap.get(LAST_NAME);
                        eMail = answerMap.get(E_MAIL);
                        phone = answerMap.get(PHONE);
                        region = answerMap.get(REGION);
                        city = answerMap.get(CITY);
                        request.setAttribute(FIRST_NAME, firstName);
                        request.setAttribute(LAST_NAME, lastName);
                        request.setAttribute(E_MAIL, eMail);
                        request.setAttribute(PHONE, phone);
                        request.setAttribute(REGION, region);
                        request.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
                        break;
                    case DUPLICATE_EMAIL:
                        request.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_EMAIL);
                        request.setAttribute(FIRST_NAME, firstName);
                        request.setAttribute(LAST_NAME, lastName);
                        request.setAttribute(E_MAIL, EMPTY_VALUE);
                        request.setAttribute(PHONE, phone);
                        request.setAttribute(REGION, region);
                        request.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
                        break;

                    case DUPLICATE_PHONE:
                        request.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE);
                        request.setAttribute(FIRST_NAME, firstName);
                        request.setAttribute(LAST_NAME, lastName);
                        request.setAttribute(E_MAIL, eMail);
                        request.setAttribute(PHONE, EMPTY_VALUE);
                        request.setAttribute(REGION, region);
                        request.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
                        break;
                    case DUPLICATE_EMAIL_AND_PHONE:
                        request.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE);
                        request.setAttribute(FIRST_NAME, firstName);
                        request.setAttribute(LAST_NAME, lastName);
                        request.setAttribute(E_MAIL, EMPTY_VALUE);
                        request.setAttribute(PHONE, EMPTY_VALUE);
                        request.setAttribute(REGION, region);
                        request.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
                        break;
                    default:
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
                        logger.error("Result check data is incorrect." + answerMap);
                }
            } else {
                logger.error("Result check data is incorrect." + answerMap);
                router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("User hasn't been registered", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
