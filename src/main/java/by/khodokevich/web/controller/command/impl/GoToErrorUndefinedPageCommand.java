package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.*;
import jakarta.servlet.http.HttpServletRequest;

public class GoToErrorUndefinedPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.ERROR404_PAGE, Router.RouterType.REDIRECT);
    }
}
