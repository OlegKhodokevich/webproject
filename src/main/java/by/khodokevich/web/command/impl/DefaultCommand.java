package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAndAttributeType;
import by.khodokevich.web.command.Router;

import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        gggggggggggggggggggggggggggggggggggggggggggg
        request.getSession().setAttribute(ParameterAndAttributeType.CURRENT_PAGE, PagePath.ERROR404_PAGE);
        Router router = new Router(PagePath.TO_ERROR404_PAGE, Router.RouterType.REDIRECT);
        return router;
    }
}
