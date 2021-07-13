package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToMainCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_MAIN_PAGE);
        Router router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
