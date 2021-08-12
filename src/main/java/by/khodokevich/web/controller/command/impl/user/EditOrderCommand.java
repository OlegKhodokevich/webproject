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

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.MESSAGE;

public class EditOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditOrderCommand.class);

    public static final String KEY_ORDER_EDIT = "order.message_edit";
    public static final String KEY_DATA_INCORRECT = "order.message_data_incorrect";
    public static final String KEY_UNKNOWN_USER = "order.message_user_unknown";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start EditOrderCommand.");
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        Map<String, String> orderData = RequestData.getRequestOrderData(request);
        HttpSession session = request.getSession();

        try {
            Map<String, String> answerMap = orderService.updateOrder(orderData);
            String operationResultString = answerMap.get(RESULT);
            CheckingResult resultOperation;
            if (operationResultString != null) {
                resultOperation = CheckingResult.valueOf(operationResultString);
                switch (resultOperation) {
                    case SUCCESS -> {
                        session.setAttribute(MESSAGE, KEY_ORDER_EDIT);
                        router = new Router(PagePath.TO_USERS_ORDERS + "&userId=" + orderData.get(USER_ID), Router.RouterType.REDIRECT);

                    }
                    case NOT_VALID -> {
                        session.setAttribute(MESSAGE, KEY_DATA_INCORRECT);
                        session.setAttribute(TITLE, answerMap.get(TITLE));
                        session.setAttribute(DESCRIPTION, answerMap.get(DESCRIPTION));
                        session.setAttribute(ADDRESS, answerMap.get(ADDRESS));
                        session.setAttribute(COMPLETION_DATE, answerMap.get(COMPLETION_DATE));
                        session.setAttribute(SPECIALIZATION, answerMap.get(SPECIALIZATION));
                        router = new Router(PagePath.CREATION_ORDER_PAGE, Router.RouterType.REDIRECT);
                    }
                    case USER_UNKNOWN -> {
                        session.setAttribute(MESSAGE, KEY_UNKNOWN_USER);
                        router = new Router(PagePath.MAIN_PAGE, Router.RouterType.REDIRECT);
                    }
                    case ERROR -> router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
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
            logger.error("Can't edit order", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
