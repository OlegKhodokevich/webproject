package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Executor;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.ExecutorService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class FindExecutorBySpecializationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindExecutorBySpecializationCommand.class);
    private static final String EXECUTOR_NOT_FOUND = "executor.empty_list";
    private static final String CHECKBOX_VALUE = "on";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindOrdersBySpecializationCommand.");
        List<Executor> specifiedExecutorList;
        List<Specialization> specializations = new ArrayList<>();
        Router router;
        ExecutorService executorService = ServiceProvider.EXECUTOR_SERVICE;
        HttpSession session = request.getSession();
        try {

            Map<String, Specialization> specializationMap = Specialization.getSpecializationMap();
            Set<String> keys = specializationMap.keySet();
            for (String key : keys) {
                logger.info("key = |" + key + "| ,value =" + request.getParameter(key));
                String value = request.getParameter(key);
                if (value != null && value.equals(CHECKBOX_VALUE)) {
                    specializations.add(specializationMap.get(key));
                    session.setAttribute(key, "on");
                } else {
                    session.setAttribute(key, "");
                }
            }
            logger.debug("Specs = " + specializations);
            List<Executor> allExecutorList = executorService.findAllExecutors();
            if (!specializations.isEmpty()) {
                specifiedExecutorList = allExecutorList.stream().filter(s -> s.getExecutorOption().getSkills().stream().peek(e -> logger.debug("specialization = " + e.getSpecialization())).anyMatch((e) -> specializations.contains(e.getSpecialization()))).toList();
            } else {
                specifiedExecutorList = allExecutorList;
            }
            logger.info("Executors = " + specifiedExecutorList);
            if (specifiedExecutorList.isEmpty()) {
                session.setAttribute(MESSAGE, EXECUTOR_NOT_FOUND);
            }
            session.setAttribute(EXECUTOR_LIST, specifiedExecutorList);
            router = new Router(PagePath.EXECUTORS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Error during query", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
