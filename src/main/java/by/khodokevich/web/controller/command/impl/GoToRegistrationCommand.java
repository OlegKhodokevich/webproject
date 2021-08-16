package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.REGISTER_PAGE, Router.RouterType.FORWARD);
    }

}
