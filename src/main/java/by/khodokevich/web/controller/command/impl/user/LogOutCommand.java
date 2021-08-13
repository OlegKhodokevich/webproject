package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.*;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start log out. ");
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(ACTIVE_USER);
            String role = (String) session.getAttribute(ACTIVE_USER_ROLE);
            logger.info("User exited from account. User  = " + user + "  , role  = " + role);
            String locale = (String) session.getAttribute(LOCALE);
            session.invalidate();
            session = request.getSession();
            session.setAttribute(ACTIVE_USER_ROLE, UserRole.GUEST.name());
            session.setAttribute(LOCALE, locale);
        }
        return new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
    }
}
