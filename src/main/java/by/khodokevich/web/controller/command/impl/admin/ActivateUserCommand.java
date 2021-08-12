package by.khodokevich.web.controller.command.impl.admin;

import by.khodokevich.web.controller.command.*;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;


public class ActivateUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivateUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        CheckingResult resultOperation;

        String eMail = request.getParameter(E_MAIL);
        String token = request.getParameter(TOKEN);
        UserService userService = ServiceProvider.USER_SERVICE;
        try {
            resultOperation = userService.activateUser(eMail, token);
        } catch (ServiceException e) {
            logger.error("Can't activate user. Email = " + eMail);
            resultOperation = CheckingResult.ERROR;
        }
        switch (resultOperation) {
            case SUCCESS -> {
                request.setAttribute(ParameterAttributeType.USER_MESSAGE, InformationMessage.USER_ACTIVATE);
                router = new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
            }
            case USER_UNKNOWN -> {
                request.setAttribute(USER_MESSAGE, InformationMessage.ERROR_USER_UNKNOWN);
                router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
            }
            case USER_STATUS_NOT_DECLARED -> {
                request.setAttribute(USER_MESSAGE, InformationMessage.ERROR_USER_STATUS_NOT_DECLARED);
                router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
            }
            case ERROR -> router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            default -> {
                logger.error("That CheckingResult not exist.");
                throw new EnumConstantNotPresentException(CheckingResult.class, resultOperation.name());
            }
        }
        return router;
    }
}