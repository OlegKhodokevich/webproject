package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.RequestData;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

/**
 * This class activate user order.
 * It sets open status and update order information in database
 */
public class ActivateOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivateOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start ActivateOrderCommand.");
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        try {
            Map<String, String> orderData = RequestData.getRequestOrderData(request);
            Map<String, String> answerMap = orderService.updateOrder(orderData);
            String operationResultString = answerMap.get(RESULT);
            HttpSession session = request.getSession();
            CheckingResult resultOperation;
            if (operationResultString != null) {
                resultOperation = CheckingResult.valueOf(operationResultString);
                switch (resultOperation) {
                    case SUCCESS -> {
                        session.setAttribute(MESSAGE, KEY_ORDER_ACTIVATED);
                        router = new Router(PagePath.TO_USERS_ORDERS + "&userId=" + orderData.get(USER_ID), REDIRECT);
                    }
                    case NOT_VALID -> {
                        session.setAttribute(MESSAGE, KEY_DATA_INCORRECT);
                        session.setAttribute(TITLE, answerMap.get(TITLE));
                        session.setAttribute(DESCRIPTION, answerMap.get(DESCRIPTION));
                        session.setAttribute(ADDRESS, answerMap.get(ADDRESS));
                        session.setAttribute(COMPLETION_DATE, answerMap.get(COMPLETION_DATE));
                        session.setAttribute(SPECIALIZATION, answerMap.get(SPECIALIZATION));
                        router = new Router(PagePath.CREATION_ORDER_PAGE, REDIRECT);
                    }
                    case USER_UNKNOWN -> {
                        session.setAttribute(MESSAGE, KEY_UNKNOWN_USER);
                        router = new Router(PagePath.MAIN_PAGE, REDIRECT);
                    }
                    case ERROR -> router = new Router(PagePath.ERROR_PAGE, REDIRECT);
                    default -> {
                        logger.error("That CheckingResult not exist.");
                        throw new EnumConstantNotPresentException(CheckingResult.class, resultOperation.name());
                    }
                }
            } else {
                logger.error("Result check data is incorrect." + answerMap);
                router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't activate order", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
