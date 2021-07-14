package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_LOGIN_PAGE);
        Router router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
