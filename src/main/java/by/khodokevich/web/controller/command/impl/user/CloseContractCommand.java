package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.ContractService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class close contract.
 * It set completed status.
 * And set close status for order.
 * Update information about contracts for executor.
 */
public class CloseContractCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CloseContractCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        ContractService contractService = ServiceProvider.CONTRACT_SERVICE;
        try {
            String contractIdString = request.getParameter(CONTRACT_ID);
            String orderIdString = request.getParameter(ORDER_ID);
            long contractId = Long.parseLong(contractIdString);
            long orderId = Long.parseLong(orderIdString);
            boolean result = contractService.setCompletedStatus(contractId, orderId);
            HttpSession session = request.getSession();
            long activeUser = (Long) session.getAttribute(ACTIVE_USER_ID);
            if (result) {
                router = new Router(PagePath.TO_MY_CONTRACT + "&userId=" + activeUser, Router.RouterType.REDIRECT);
            } else {
                logger.error("Can't complete contract");
                router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't complete contract", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
