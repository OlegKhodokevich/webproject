package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Contract;
import by.khodokevich.web.model.service.ContractService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class search executor contracts.
 */
public class FindContractByExecutorIdCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindContractByCustomerIdCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindContractByExecutorIdCommand;");
        Router router;

        try {
            String executorIdString = request.getParameter(EXECUTOR_ID);
            long executorId = Long.parseLong(executorIdString);
            ContractService contractService = ServiceProvider.CONTRACT_SERVICE;
            List<Contract> contracts = contractService.findConcludedContractByUserExecutorId(executorId);
            logger.info(" ContractList = " + contracts);
            HttpSession session = request.getSession();
            session.setAttribute(CONTRACT_LIST, contracts);
            router = new Router(PagePath.MY_CONTRACTS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("UserId has incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}

