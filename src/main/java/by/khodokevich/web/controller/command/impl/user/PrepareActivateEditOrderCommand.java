package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

public class PrepareActivateEditOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrepareActivateEditOrderCommand.class);
    private static final String MESSAGE_UNSUPPORTED_OPERATION = "project.unsupported_operation";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start PrepareActivateEditOrderCommand.");
        Router router;
        long orderId = Long.parseLong(request.getParameter(ORDER_ID));
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        HttpSession session = request.getSession();
        String reason = request.getParameter(REASON);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            Optional<Order> optionalOrder = orderService.findDefineOrder(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                Date completionDate = order.getCompletionDate();
                String completionDateString = formatter.format(completionDate);
                session.setAttribute(ORDER_ID, orderId);
                session.setAttribute(USER_ID, order.getUserId());
                session.setAttribute(TITLE, order.getTitle());
                session.setAttribute(DESCRIPTION, order.getDescription());
                session.setAttribute(ADDRESS, order.getAddress());
                session.setAttribute(COMPLETION_DATE, completionDateString);
                session.setAttribute(SPECIALIZATION, order.getSpecialization());
                session.setAttribute(REASON, reason);
                router = new Router(PagePath.CREATION_ORDER_PAGE, REDIRECT);
            } else {
                session.setAttribute(ORDER, null);
                session.setAttribute(MESSAGE, MESSAGE_UNSUPPORTED_OPERATION);
                router = new Router(PagePath.MY_ORDERS, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't archive order " + orderId, e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
