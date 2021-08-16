package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;

public class
GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.LOGIN_PAGE, Router.RouterType.FORWARD);
    }
}
