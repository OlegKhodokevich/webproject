package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.*;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.CheckingResultType;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.command.ParameterAndAttributeType.*;


public class ActivateUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        CheckingResultType resultOperation;

        HttpSession session = request.getSession();
        String eMail = request.getParameter(E_MAIL);;
        String token = request.getParameter(TOKEN);
        UserService userService = new UserServiceImpl();
        try {
            resultOperation = userService.activateUser(eMail, token);
        } catch (ServiceException e) {
            logger.error("Can't activate user. Email = " + eMail);
            resultOperation = CheckingResultType.ERROR;
        }
        switch (resultOperation) {
            case SUCCESS:
                request.setAttribute(ParameterAndAttributeType.USER_MESSAGE, InformationMessage.USER_ACTIVATE);
                router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
                break;
            case USER_UNKNOWN:
                request.setAttribute(USER_MESSAGE, InformationMessage.ERROR_USER_UNKNOWN);
                router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                break;
            case USER_STATUS_NOT_DECLARED:
                request.setAttribute(USER_MESSAGE, InformationMessage.ERROR_USER_STATUS_NOT_DECLARED);
                router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                break;
            case ERROR:
                router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
                break;
            default:
                logger.error("Unknown operation result.");
                router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
