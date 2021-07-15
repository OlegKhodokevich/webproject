package by.khodokevich.web.command.impl;

import static by.khodokevich.web.command.ParameterAttributeType.*;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.service.CheckingResult;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterUserCommand.class);

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
        UserService userService = ServiceProvider.USER_SERVICE;
        Map<String, String> answerMap;
        CheckingResult resultOperation;
        HttpSession session = request.getSession();
        try {
            answerMap = userService.register(userData);
            String result = answerMap.get(RESULT);
            if (result != null) {
                resultOperation = CheckingResult.valueOf(result);
                switch (resultOperation) {
                    case SUCCESS:
                        router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                        session.setAttribute(MESSAGE, KEY_SENT_MASSAGE);
                        break;
                    case NOT_VALID:
                        request.setAttribute(MESSAGE, KEY_MESSAGE_DATA_NOT_CORRECT);
                        firstName = answerMap.get(FIRST_NAME);
                        lastName = answerMap.get(LAST_NAME);
                        eMail = answerMap.get(E_MAIL);
                        phone = answerMap.get(PHONE);
                        region = answerMap.get(REGION);
                        city = answerMap.get(CITY);
                        session.setAttribute(FIRST_NAME, firstName);
                        session.setAttribute(LAST_NAME, lastName);
                        session.setAttribute(E_MAIL, eMail);
                        session.setAttribute(PHONE, phone);
                        session.setAttribute(REGION, region);
                        session.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case DUPLICATE_EMAIL:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_EMAIL);
                        session.setAttribute(FIRST_NAME, firstName);
                        session.setAttribute(LAST_NAME, lastName);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        session.setAttribute(PHONE, phone);
                        session.setAttribute(REGION, region);
                        session.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;

                    case DUPLICATE_PHONE:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE);
                        session.setAttribute(FIRST_NAME, firstName);
                        session.setAttribute(LAST_NAME, lastName);
                        session.setAttribute(E_MAIL, eMail);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        session.setAttribute(REGION, region);
                        session.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case DUPLICATE_EMAIL_AND_PHONE:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE);
                        session.setAttribute(FIRST_NAME, firstName);
                        session.setAttribute(LAST_NAME, lastName);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        session.setAttribute(REGION, region);
                        session.setAttribute(CITY, city);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    default:
                        logger.error("That CheckingResult not exist.");
                        throw new EnumConstantNotPresentException(CheckingResult.class, resultOperation.name());
                }
            } else {
                logger.error("Result check data is incorrect." + answerMap);
                router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("User hasn't been registered", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
