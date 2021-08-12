package by.khodokevich.web.controller.command.impl.admin;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.service.ServiceProvider;
import by.khodokevich.web.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

public class AllUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AllUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start AllUserCommand.");
        Router router;
        UserService userService = ServiceProvider.USER_SERVICE;
        HttpSession session = request.getSession();

        try {
            List<User> users = userService.findAllUser();
            session.setAttribute(USER_LIST, users);
            router =new Router(PagePath.ALL_USERS, FORWARD);
        } catch (ServiceException e) {
            logger.error("Can't find all users.", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
