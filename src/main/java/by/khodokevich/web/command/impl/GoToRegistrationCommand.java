package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToRegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToRegistrationCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_REGISTER_PAGE);
        Router router = new Router(PagePath.REGISTER_PAGE, Router.RouterType.REDIRECT);

        return router;
    }

}
