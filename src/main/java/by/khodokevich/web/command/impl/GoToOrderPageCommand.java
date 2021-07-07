package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAndAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.dao.OrderDao;
import by.khodokevich.web.dao.impl.OrderDaoImpl;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserServiceImpl.OrderServiceImpl;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToOrderPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = new OrderServiceImpl();
        HttpSession session = request.getSession();
        try {
            List<Order> orderList = orderService.findAllOrder();
            if (!orderList.isEmpty()) {
//                request.setAttribute("orderList", orderList);
                session.setAttribute("orderList", orderList);
            } else {
                request.setAttribute(ParameterAndAttributeType.EMPTY_LIST, Boolean.valueOf(true));
            }
            session.setAttribute(ParameterAndAttributeType.CURRENT_PAGE, PagePath.ORDERS);
            router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Can't find all orders",e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
