package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.Impl.RevokeServiceImpl;
import by.khodokevich.web.model.service.RevokeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

public class CreateRevokeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateRevokeCommand.class);
    private static final String MESSAGE_FAILED = "project.unsupported_operation";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;

        RevokeService revokeService = new RevokeServiceImpl();
        String contractIdString = request.getParameter(CONTRACT_ID);
        String markString = request.getParameter(RATING);
        String description = request.getParameter(DESCRIPTION_REVOKE);
        HttpSession session = request.getSession();
        Long activeUserId = (Long) session.getAttribute(ACTIVE_USER_ID);
        try {
            if (activeUserId != null) {
                if (revokeService.createRevoke(contractIdString, description, markString)){
                    router = new Router(PagePath.TO_MY_CONTRACT + "&userId=" + activeUserId, REDIRECT);
                } else {
                    session.setAttribute(MESSAGE, MESSAGE_FAILED);
                    router = new Router(PagePath.TO_MY_CONTRACT + "&userId=" + activeUserId, REDIRECT);
                }

            } else {

                router = new Router(PagePath.MAIN_PAGE, REDIRECT);
            }

        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't make revoke.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
