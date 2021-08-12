package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.RequestData;
import by.khodokevich.web.model.builder.UserBuilder;
import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);
    public static final String KEY_STATUS_ARCHIVED = "error.status_archived";
    public static final String KEY_DATA_NOT_VALID = "registration.message_data_not_correct";
    public static final String KEY_USER_UNKNOWN = "error.user_unknown";
    public static final String KEY_USER_NOT_CONFIRMED = "error.user_not_confirmed";
    private static final String MASSAGE_LETTER_NOT_SENT = "mail_sender.letter_not_send";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start sign in.");
        Router router;
        Map<String, String> userData = RequestData.getRequestUserData(request);
        UserService userService = ServiceProvider.USER_SERVICE;
        Map<String, String> answerMap;
        HttpSession session = request.getSession();
        try {
            answerMap = userService.logOn(userData);
            CheckingResult resultType = CheckingResult.valueOf(answerMap.get(RESULT));
            switch (resultType) {
                case SUCCESS -> {
                    long userId = Long.parseLong(answerMap.get(USER_ID));
                    RegionBelarus regionBelarus = RegionBelarus.valueOf(answerMap.get(REGION));
                    UserRole role = UserRole.valueOf(answerMap.get(ROLE));
                    UserStatus status = UserStatus.valueOf(answerMap.get(STATUS));
                    User user = new UserBuilder()
                            .userId(userId)
                            .firstName(answerMap.get(FIRST_NAME))
                            .lastName(answerMap.get(LAST_NAME))
                            .eMail(answerMap.get(E_MAIL))
                            .phone(answerMap.get(PHONE))
                            .region(regionBelarus)
                            .city(answerMap.get(CITY))
                            .status(status)
                            .role(role)
                            .buildUser();
                    session.setAttribute(ACTIVE_USER_ROLE, role.name());
                    session.setAttribute(ACTIVE_USER, user);
                    session.setAttribute(ACTIVE_USER_ID, user.getIdUser());
                    router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                }
                case USER_UNKNOWN -> {
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    session.setAttribute(MESSAGE, KEY_USER_UNKNOWN);
                }
                case NOT_VALID -> {
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    session.setAttribute(MESSAGE, KEY_DATA_NOT_VALID);
                }
                case USER_STATUS_NOT_CONFIRM -> {
                    session.setAttribute(MESSAGE, KEY_USER_NOT_CONFIRMED);
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                }
                case USER_STATUS_IS_ARCHIVED -> {
                    session.setAttribute(MESSAGE, KEY_STATUS_ARCHIVED);
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                }
                case LETTER_NOT_SENT -> {
                    session.setAttribute(MESSAGE, MASSAGE_LETTER_NOT_SENT);
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                }
                default -> throw new UnsupportedOperationException();
            }
        } catch (ServiceException e) {
            logger.error("Log In Error", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        logger.info("End sign in.");
        return router;
    }
}
