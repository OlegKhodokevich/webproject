package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.*;
import by.khodokevich.web.model.builder.UserBuilder;
import by.khodokevich.web.model.entity.Region;
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

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

/**
 * This class edit(update) user in database
 */
public class EditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditUserCommand.class);

    private static final String EMPTY_VALUE = "";
    private static final String VALUE_CHANGE_PASSWORD = "on";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start EditUserCommand.");
        Router router;
        Map<String, String> userData = RequestData.getRequestUserData(request);
        Map<String, String> answerMap;
        CheckingResult resultOperation;
        HttpSession session = request.getSession();

        try {
            UserService userService = ServiceProvider.USER_SERVICE;
            String changePassword = request.getParameter(CHANGE_PASSWORD);

            if (changePassword == null || !changePassword.equalsIgnoreCase(VALUE_CHANGE_PASSWORD)) {
                answerMap = userService.updateUserWithoutPassword(userData);
            } else {
                answerMap = userService.updateUser(userData);
            }

            String result = answerMap.get(RESULT);
            if (result != null) {

                resultOperation = CheckingResult.valueOf(result);
                switch (resultOperation) {
                    case SUCCESS:
                        long userId = Long.parseLong(userData.get(USER_ID));
                        Region region = Region.valueOf(userData.get(REGION));
                        String roleString = answerMap.get(ROLE);
                        UserRole role = UserRole.valueOf(roleString);
                        String statusString = answerMap.get(STATUS);
                        UserStatus status = UserStatus.valueOf(statusString);
                        User user = new UserBuilder()
                                .userId(userId)
                                .firstName(userData.get(FIRST_NAME))
                                .lastName(userData.get(LAST_NAME))
                                .eMail(userData.get(E_MAIL))
                                .phone(userData.get(PHONE))
                                .region(region)
                                .city(userData.get(CITY))
                                .status(status)
                                .role(role)
                                .buildUser();
                        session.setAttribute(USER, user);
                        User activeUser = (User) session.getAttribute(ACTIVE_USER);
                        if (activeUser.getRole() != UserRole.ADMIN && activeUser.getIdUser() == userId) {
                            session.setAttribute(ACTIVE_USER, user);
                        }
                        router = new Router(PagePath.USER_INFO, Router.RouterType.REDIRECT);
                        break;
                    case SUCCESS_WITH_SEND_EMAIL:
                        userId = Long.parseLong(userData.get(REGION));
                        region = Region.valueOf(userData.get(REGION));
                        roleString = answerMap.get(ROLE);
                        role = UserRole.valueOf(roleString);
                        statusString = answerMap.get(STATUS);
                        status = UserStatus.valueOf(statusString);
                        user = new UserBuilder()
                                .userId(userId)
                                .firstName(userData.get(FIRST_NAME))
                                .lastName(userData.get(LAST_NAME))
                                .eMail(userData.get(E_MAIL))
                                .phone(userData.get(PHONE))
                                .region(region)
                                .city(userData.get(CITY))
                                .status(status)
                                .role(role)
                                .buildUser();
                        session.setAttribute(USER, user);
                        activeUser = (User) session.getAttribute(ACTIVE_USER);
                        if (activeUser.getRole() != UserRole.ADMIN && activeUser.getIdUser() == userId) {
                            router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                            session.invalidate();
                            session = request.getSession();
                            session.setAttribute(MESSAGE, KEY_SENT_MASSAGE);
                        } else {
                            router = new Router(PagePath.USER_INFO, Router.RouterType.REDIRECT);
                        }
                        break;
                    case NOT_VALID:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DATA_NOT_CORRECT);
                        SessionData.setSessionUserData(answerMap, session);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case DUPLICATE_EMAIL:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_EMAIL);
                        SessionData.setSessionUserData(answerMap, session);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case DUPLICATE_PHONE:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE);
                        SessionData.setSessionUserData(answerMap, session);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case DUPLICATE_EMAIL_AND_PHONE:
                        session.setAttribute(MESSAGE, KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE);
                        SessionData.setSessionUserData(answerMap, session);
                        session.setAttribute(E_MAIL, EMPTY_VALUE);
                        session.setAttribute(PHONE, EMPTY_VALUE);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case LETTER_NOT_SENT:
                        session.setAttribute(MESSAGE, MASSAGE_LETTER_NOT_SENT);
                        router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case USER_UNKNOWN:
                        session.setAttribute(MESSAGE, MASSAGE_USER_UNKNOWN);
                        router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                        break;
                    case ERROR:
                        logger.error("User hasn't been updated");
                        router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
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
            logger.error("User hasn't been updated", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("Can't parse userId.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
