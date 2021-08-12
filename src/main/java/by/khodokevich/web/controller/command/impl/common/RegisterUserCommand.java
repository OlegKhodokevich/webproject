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

public class RegisterUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterUserCommand.class);

    private static final String KEY_SENT_MASSAGE = "registration.message_send_link";
    private static final String KEY_MESSAGE_DATA_NOT_CORRECT = "registration.message_data_not_correct";
    private static final String KEY_MESSAGE_DUPLICATE_EMAIL = "registration.message_duplicate_email";
    private static final String KEY_MESSAGE_DUPLICATE_PHONE = "registration.message_duplicate_phone";
    private static final String KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE = "registration.message_duplicate_email_and_phone";
    private static final String EMPTY_VALUE = "";
    private static final String MASSAGE_LETTER_NOT_SENT = "mail_sender.letter_not_send";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserService userService = ServiceProvider.USER_SERVICE;
        Map<String,String> userData = RequestData.getRequestUserData(request);
        CheckingResult resultOperation;
        HttpSession session = request.getSession();
        try {
            Map<String, String> answerMap = userService.register(userData);
            String result = answerMap.get(RESULT);
            if (result != null) {
                resultOperation = CheckingResult.valueOf(result);
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
