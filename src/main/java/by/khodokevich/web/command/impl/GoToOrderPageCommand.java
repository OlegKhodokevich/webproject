package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToOrderPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToOrderPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        HttpSession session = request.getSession();
        try {
            List<Order> orderList = orderService.findAllOrder();
            if (!orderList.isEmpty()) {
//                request.setAttribute("orderList", orderList);
                session.setAttribute("orderList", orderList);
                router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);
                session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_ORDERS);
            } else {
//                request.setAttribute(ParameterAttributeType.EMPTY_LIST, Boolean.valueOf(true));
                router = new Router(PagePath.ORDERS + "?" +ParameterAttributeType.EMPTY_LIST + "=true", Router.RouterType.REDIRECT);
                session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_ORDERS);
            }

        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
