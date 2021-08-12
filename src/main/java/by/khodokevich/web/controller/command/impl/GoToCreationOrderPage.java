package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.khodokevich.web.controller.command.ParameterAttributeType.REASON;

public class GoToCreationOrderPage implements Command {
    private static final String REASON_CREATE = "create";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(REASON, REASON_CREATE);
        return new Router(PagePath.CREATION_ORDER_PAGE, Router.RouterType.REDIRECT);
    }
}