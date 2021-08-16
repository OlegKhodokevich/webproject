package by.khodokevich.web.controller.command.impl;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.SPECIALIZATION;

public class GoToCreationRevokePageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String contractIdString = request.getParameter(CONTRACT_ID);
        request.setAttribute(CONTRACT_ID, contractIdString);
        session.setAttribute(DESCRIPTION, "");
        return new Router(PagePath.CREATE_REVOKE, Router.RouterType.FORWARD);
    }
}
