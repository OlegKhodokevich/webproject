package by.khodokevich.web.controller.command.impl.common;

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

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class AllOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AllOrderCommand.class);
    private static final String ORDERS_NOT_FOUND = "order.empty_list";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        HttpSession session = request.getSession();
        try {
            List<Order> orderList = orderService.findOpenOrderConfirmedUsers();
            if (orderList.isEmpty()) {
                session.setAttribute(MESSAGE, ORDERS_NOT_FOUND);
            }
            session.setAttribute(ORDER_LIST, orderList);
            router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
