package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.CheckingResultType;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.command.ParameterAndAttributeType.*;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String KEY_STATUS_ARCHIVED = "error.status_archived";

    public static final String KEY_DATA_NOT_VALID = "registration.message_data_not_correct";
    public static final String KEY_USER_UNKNOWN = "error.user_unknown";
    public static final String KEY_USER_NOT_CONFIRMED = "error.user_not_confirmed";


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String eMail = request.getParameter(E_MAIL);
        String password = request.getParameter(PASSWORD);
        Map<String, String> userData = new HashMap<>();
        userData.put(E_MAIL, eMail);
        userData.put(PASSWORD, password);
        userData.put(URL, request.getRequestURL().toString());
        UserService userService = new UserServiceImpl();
        Map<String, String> answerMap = new HashMap<>();
        try {
            answerMap = userService.logOn(userData);
            CheckingResultType resultType = CheckingResultType.valueOf(answerMap.get(RESULT));
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
                    switch (status) {
                        case CONFIRMED:
                            HttpSession session = request.getSession();
                            User user = new User(userId, firstName, lastName, eMail, phone, regionBelarus, city, status, role);
                            session.setAttribute(ACTIVE_USER, user);
                            session.setAttribute(ACTIVE_USER_ROLE, user.getRole().name());
                            session.setAttribute(ACTIVE_USER_STATUS, user.getStatus().name());
                            router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                            String userName = user.getfirstName();
                            String userSurname = user.getlastName();
                            String messege = userName + " " + userSurname + " ";
                            request.setAttribute(MESSAGE, messege);
                            break;
                        case DECLARED:
                            router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                            request.setAttribute(MESSAGE, KEY_USER_NOT_CONFIRMED);

                            break;
                        case ARCHIVED:
                            router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                            request.setAttribute(MESSAGE, KEY_STATUS_ARCHIVED);
                            break;
                        default:
                            logger.error("Log In Error. Default status." + userId);
                            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
                    }
                case USER_UNKNOWN:
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.FORWARD);
                    request.setAttribute(MESSAGE, KEY_USER_UNKNOWN);
                    break;
                case NOT_VALID:
                    router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.FORWARD);
                    request.setAttribute(MESSAGE, KEY_DATA_NOT_VALID);
                    break;
                default:
                    logger.error("Log In Error. Default e-mail :" + eMail);
                    router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            }
//
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                switch (user.getStatus()) {
//                    case CONFIRMED:
//                        HttpSession session = request.getSession();
//                        session.setAttribute(ACTIVE_USER, user);
//                        session.setAttribute(ACTIVE_USER_ROLE, user.getRole().name());
//                        session.setAttribute(ACTIVE_USER_STATUS, user.getStatus().name());
//                        router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
//                        String userName = user.getfirstName();
//                        String userSurname = user.getlastName();
//                        String messege = userName + " " + userSurname + " ";
//                        request.setAttribute(MESSAGE, messege);
//                        break;
//                    case DECLARED:
//                        router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
//                        request.setAttribute(MESSAGE, KEY_USER_NOT_CONFIRMED);
//
//                        break;
//                    case ARCHIVED:
//                        router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
//                        request.setAttribute(MESSAGE, KEY_STATUS_ARCHIVED);
//                        break;
//                    default:
//                        logger.error("Log In Error. Default status." + user);
//                        router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
//                }
//            } else {
//                router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.FORWARD);
//                request.setAttribute(MESSAGE, KEY_USER_UNKNOWN);
//            }


        } catch (ServiceException e) {
            logger.error("Log In Error", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
