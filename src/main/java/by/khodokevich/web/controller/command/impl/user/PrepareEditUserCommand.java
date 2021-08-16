package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class search user information from database and forward to user page for edition.
 */
public class PrepareEditUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrepareEditUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start PrepareEditUserCommand.");
        Router router;

        HttpSession session = request.getSession();
        try {
            long userId = Long.parseLong(request.getParameter(USER_ID));
            UserService userService = ServiceProvider.USER_SERVICE;
            Optional<User> optionalUser = userService.findDefineUser(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER_ID, user.getIdUser());
                session.setAttribute(FIRST_NAME, user.getFirstName());
                session.setAttribute(LAST_NAME, user.getLastName());
                session.setAttribute(E_MAIL, user.getEMail());
                session.setAttribute(PHONE, user.getPhone());
                session.setAttribute(REGION, user.getRegion());
                session.setAttribute(CITY, user.getCity());
                router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);
                if (user.getRole().name().equals("EXECUTOR")) {
                    session.setAttribute(MESSAGE, EDIT_REST_DATA);
                }
            } else {
                logger.error("Error user's information has been deleted. User id =" + userId);
                session.setAttribute(MESSAGE, USER_NOT_FOUND);
                router = new Router(CURRENT_PAGE, Router.RouterType.REDIRECT);
            }

        } catch (ServiceException e) {
            logger.error("User can't be prepared for editing", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("UserId has incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
