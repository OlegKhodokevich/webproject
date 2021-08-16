package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Revoke;
import by.khodokevich.web.model.service.RevokeService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.FORWARD;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

/**
 * This class search executor's revoke.
 */
public class FindExecutorRevokeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindExecutorRevokeCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String executorIdString = request.getParameter(EXECUTOR_ID);
        try {
            long executorId = Long.parseLong(executorIdString);
            RevokeService revokeService = ServiceProvider.REVOKE_SERVICE;
            List<Revoke> revokeList = revokeService.findAllExecutorRevoke(executorId);
            HttpSession session = request.getSession();
            session.setAttribute(REVOKE_LIST, revokeList);
            router = new Router(PagePath.REVOKE, FORWARD);
        } catch (NumberFormatException e) {
            logger.error("Can't parse id.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Error during query", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }

        return router;
    }
}
