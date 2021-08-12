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

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

public class AllExecutorCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AllExecutorCommand.class);
    private static final String EXECUTOR_NOT_FOUND = "executor.empty_list";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start AllExecutorCommand.");
        Router router;
        ExecutorService executorService = ServiceProvider.EXECUTOR_SERVICE;
        HttpSession session = request.getSession();
        try {
            List<Executor> executorList = executorService.findAllExecutors();
            if (executorList.isEmpty()) {
                session.setAttribute(MESSAGE, EXECUTOR_NOT_FOUND);
            }
            session.setAttribute(EXECUTOR_LIST, executorList);
            router = new Router(PagePath.EXECUTORS, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
