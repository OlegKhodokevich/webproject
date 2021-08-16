package by.khodokevich.web.controller.command.impl.common;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

import by.khodokevich.web.controller.command.*;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import static by.khodokevich.web.controller.command.InformationMessage.*;

/**
 * This class register new user in system.
 * User is registered as customer.
 */
public class RegisterUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterUserCommand.class);

    private static final String EMPTY_VALUE = "";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        Map<String, String> userData = RequestData.getRequestUserData(request);
        CheckingResult resultOperation;
        try {
            UserService userService = ServiceProvider.USER_SERVICE;
            Map<String, String> answerMap = userService.register(userData);

            String result = answerMap.get(RESULT);
            if (result != null) {
                resultOperation = CheckingResult.valueOf(result);
                HttpSession session = request.getSession();
                switch (resultOperation) {
                    case SUCCESS -> {
                        router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                        session.setAttribute(MESSAGE, KEY_SENT_MASSAGE);
                    }
                    case NOT_VALID -> {
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DATA_NOT_CORRECT);
                        SessionData.setSessionUserData(userData, session);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                    }
                    case DUPLICATE_EMAIL -> {
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_EMAIL);
                        SessionData.setSessionUserData(userData, session);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                    }
                    case DUPLICATE_PHONE -> {
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE);
                        SessionData.setSessionUserData(userData, session);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                    }
                    case DUPLICATE_EMAIL_AND_PHONE -> {
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE);
                        SessionData.setSessionUserData(userData, session);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                    }
                    case LETTER_NOT_SENT -> {
                        session.setAttribute(MESSAGE, MASSAGE_LETTER_NOT_SENT);
                        SessionData.setSessionUserData(userData, session);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                    }
                    default -> {
                        logger.error("That CheckingResult not exist.");
                        throw new EnumConstantNotPresentException(CheckingResult.class, resultOperation.name());
                    }
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
