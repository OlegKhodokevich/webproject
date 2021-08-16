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

import static by.khodokevich.web.controller.command.ParameterAttributeType.ACTIVE_USER_ID;
import static by.khodokevich.web.controller.command.ParameterAttributeType.CONTRACT_ID;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;


/**
 * This class dismiss offer.
 * Customer can dismiss offer.
 */
public class DismissContractCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DismissContractCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start DismissContractCommand.");
        Router router;
        String contractIdString = request.getParameter(CONTRACT_ID);
        long contractId = -1;

        try {
            contractId = Long.parseLong(contractIdString);
            ContractService contractService = ServiceProvider.CONTRACT_SERVICE;
            boolean result = contractService.setNotConcludedStatus(contractId);
            if (result) {
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
        }catch (ServiceException e) {
            logger.error("Can't set concludedStatus for contract with id = " + contractId, e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
