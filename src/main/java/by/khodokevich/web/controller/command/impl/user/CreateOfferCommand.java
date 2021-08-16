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

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

/**
 * This class create offer from executor to customer.
 */
public class CreateOfferCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateOfferCommand.class);


    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start CreateOfferCommand;");
        Router router;

        HttpSession session = request.getSession();
        try {
            long executorId = (Long) session.getAttribute(ACTIVE_USER_ID);
            String orderIdString = request.getParameter(ORDER_ID);
            long orderId = Long.parseLong(orderIdString);
            ContractService contractService = ServiceProvider.CONTRACT_SERVICE;
            List<Contract> executorContracts = contractService.findUnderConsiderationContractByUserExecutorId(executorId);

            boolean hasSameOffer = executorContracts.stream().anyMatch(s -> s.getOrder().getOrderId() == orderId);
            if (hasSameOffer) {
                session.setAttribute(MESSAGE, KEY_MESSAGE_RESENDING);
                router = new Router(PagePath.TO_ORDERS, REDIRECT);
            } else {
                if (contractService.createOffer(orderId, executorId)) {
                    session.setAttribute(MESSAGE, KEY_MESSAGE_SUCCESS);
                    router = new Router(PagePath.TO_ORDERS, REDIRECT);
                } else {
                    logger.error("Can't make offer.");
                    router = new Router(PagePath.ERROR_PAGE, REDIRECT);
                }
            }

        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (IllegalStateException e) {
            logger.error("Attribute class cannot be cast.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't make offer.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }

        return router;
    }
}
