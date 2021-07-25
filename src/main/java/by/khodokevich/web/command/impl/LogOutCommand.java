package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import by.khodokevich.web.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start log out. ");
        String pagePath;

        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            String role = (String) session.getAttribute("role");
            logger.info("User exited from account. User  = " + user + "  , role  = " + role);
            session.invalidate();
        }
        Router router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);

        return router;
    }
}
