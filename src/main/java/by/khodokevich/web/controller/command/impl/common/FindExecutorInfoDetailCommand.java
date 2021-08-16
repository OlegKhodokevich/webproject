package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.ExecutorService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

/**
 * This class search information about define executor.
 */
public class FindExecutorInfoDetailCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindExecutorInfoDetailCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindExecutorInfoDetailCommand.");
        Router router;
        try {
            long executorId = Long.parseLong(request.getParameter(EXECUTOR_ID));
            ExecutorService executorService = ServiceProvider.EXECUTOR_SERVICE;
            Optional<Executor> optionalExecutor = executorService.findDefineExecutor(executorId);

            HttpSession session = request.getSession();
            if (optionalExecutor.isPresent()) {
                session.setAttribute(EXECUTOR, optionalExecutor.get());
                router = new Router(PagePath.EXECUTOR_INFO, REDIRECT);
            } else {
                logger.info("Executor hasn't found.");
                session.setAttribute(MESSAGE, EXECUTOR_NOT_FOUND);
                router = new Router(PagePath.EXECUTORS, REDIRECT);
            }

        } catch (ServiceException e) {
            logger.error("Can't find executor.", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.info("Can't parse id.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
