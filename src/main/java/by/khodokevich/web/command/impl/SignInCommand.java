package by.khodokevich.web.command.impl;

import by.khodokevich.web.builder.UserBuilder;
import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.CheckingResult;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.command.ParameterAttributeType.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);
    public static final String KEY_STATUS_ARCHIVED = "error.status_archived";

    public static final String KEY_DATA_NOT_VALID = "registration.message_data_not_correct";
    public static final String KEY_USER_UNKNOWN = "error.user_unknown";
    public static final String KEY_USER_NOT_CONFIRMED = "error.user_not_confirmed";


    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start sign in.");
        Router router;
        String eMail = request.getParameter(E_MAIL);
        String password = request.getParameter(PASSWORD);
        Map<String, String> userData = new HashMap<>();
        userData.put(E_MAIL, eMail);
        userData.put(PASSWORD, password);
        userData.put(URL, request.getRequestURL().toString());
        UserService userService = ServiceProvider.USER_SERVICE;
        Map<String, String> answerMap;
        HttpSession session = request.getSession();
        try {
            answerMap = userService.logOn(userData);
            CheckingResult resultType = CheckingResult.valueOf(answerMap.get(RESULT));
            switch (resultType) {
                case SUCCESS:
                    long userId = Long.valueOf(answerMap.get(USER_ID));
                    String firstName = answerMap.get(FIRST_NAME);
                    String lastName = answerMap.get(LAST_NAME);
                    String phone = answerMap.get(PHONE);
                    String regionString = answerMap.get(REGION);
                    RegionBelarus regionBelarus = RegionBelarus.valueOf(regionString);
                    String city = answerMap.get(CITY);
                    String roleString = answerMap.get(ROLE);
                    UserRole role = UserRole.valueOf(roleString);
                    String statusString = answerMap.get(STATUS);
                    UserStatus status = UserStatus.valueOf(statusString);
                    User user = new UserBuilder()
                            .userId(userId)
                            .firstName(firstName)
                            .lastName(lastName)
                            .eMail(eMail)
                            .phone(phone)
                            .region(regionBelarus)
                            .city(city)
                            .status(status)
                            .role(role)
                            .buildUser();
                    session.setAttribute(ACTIVE_USER, user);
                    session.setAttribute(ACTIVE_USER_ID, user.getIdUser());
                    String messege = firstName + " " + lastName + " ";
                    session.setAttribute(MESSAGE, messege);
                    router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                    break;
                case USER_UNKNOWN:
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    session.setAttribute(MESSAGE, KEY_USER_UNKNOWN);
                    break;
                case NOT_VALID:
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    session.setAttribute(MESSAGE, KEY_DATA_NOT_VALID);
                    break;
                case USER_STATUS_NOT_CONFIRM:
                    session.setAttribute(MESSAGE, KEY_USER_NOT_CONFIRMED);
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    break;
                case USER_STATUS_IS_ARCHIVED:
                    session.setAttribute(MESSAGE, KEY_STATUS_ARCHIVED);
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        } catch (ServiceException e) {
            logger.error("Log In Error", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        logger.info("End sign in.");
        return router;
    }
}
