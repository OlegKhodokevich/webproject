package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class SetLocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetLocaleCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Set local command.");
        Router router;
        String newLocal = request.getParameter(LOCALE);
        if (newLocal != null) {
            HttpSession session = request.getSession();
            session.setAttribute(LOCALE, newLocal);
            String pagePath = (String) session.getAttribute(CURRENT_PAGE);

            if (pagePath == null) {
                pagePath = PagePath.MAIN_PAGE;
            }
            router = new Router(pagePath, Router.RouterType.REDIRECT);
            logger.debug("New local = " + newLocal);
        } else {
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            logger.error("locale is null.");
        }
        return router;
    }
}
