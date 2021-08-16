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
import static by.khodokevich.web.controller.command.Router.RouterType.*;

/**
 * This class search all users in database
 */
public class ConcludeContractCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ConcludeContractCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start ConcludeContractCommand.");
        Router router;
        ContractService contractService = ServiceProvider.CONTRACT_SERVICE;
        String contractIdString = request.getParameter(CONTRACT_ID);
        String orderIdString = request.getParameter(ORDER_ID);
        try {
            long contractId = Long.parseLong(contractIdString);
            long orderId = Long.parseLong(orderIdString);
            if (contractService.setConcludedStatus(contractId, orderId)) {

                HttpSession session = request.getSession();
                long activeUser = (Long) session.getAttribute(ACTIVE_USER_ID);
                router = new Router(PagePath.TO_OFFER + "&userId=" + activeUser, REDIRECT);
            } else {
                logger.error("Hasn't been set concluded status for contract with id = " + contractId);
                router = new Router(PagePath.ERROR_PAGE, REDIRECT);
            }
        } catch (NumberFormatException e) {
            logger.error("Can't parse contractId = " + contractIdString, e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't set concludedStatus for contract.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
