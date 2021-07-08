package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import by.khodokevich.web.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start log out. ");
        String pagePath;

        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                User user = (User) session.getAttribute("user");
                String role = (String) session.getAttribute("role");
                logger.info("User exited from account. User  = " + user + "  , role  = " + role);
                session.invalidate();
            }
        } catch (Throwable e) {
            logger.info(e);
        }
        Router router = new Router(PagePath.TO_MAIN_PAGE, Router.RouterType.REDIRECT);

        return router;
    }
}
