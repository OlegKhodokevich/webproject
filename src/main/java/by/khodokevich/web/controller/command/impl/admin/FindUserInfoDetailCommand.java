package by.khodokevich.web.controller.command.impl.admin;

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

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

public class FindUserInfoDetailCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserInfoDetailCommand.class);
    private static final String USER_NOT_FOUND = "user.user_not_found";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindUserInfoDetailCommand.");
        HttpSession session = request.getSession();
        UserService userService = ServiceProvider.USER_SERVICE;
        Router router;
        try {
            long userId = Long.parseLong(request.getParameter(USER_ID));
            Optional<User> optionalUser = userService.findDefineUser(userId);
            if (optionalUser.isPresent()) {
                session.setAttribute(USER, optionalUser.get());
                router = new Router(PagePath.USER_INFO, REDIRECT);
            } else {
                logger.info("Executor hasn't found.");
                session.setAttribute(MESSAGE, USER_NOT_FOUND);
                router = new Router(CURRENT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't find user.", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.info("Can't parse id.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
