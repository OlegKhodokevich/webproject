package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;

import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.TO_ERROR404_PAGE, Router.RouterType.REDIRECT);
    }
}
