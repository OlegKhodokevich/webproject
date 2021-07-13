package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class GoToMyOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToMyOrdersCommand.class);
    private static final String KEY_STATUS_ARCHIVED = "error.status_archived";


    @Override
    public Router execute(HttpServletRequest request) { //TODO not finished
        Router router;

        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        UserService userService = ServiceProvider.USER_SERVICE;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ParameterAttributeType.ACTIVE_USER);
        try {
            long userId = user.getIdUser();
            UserStatus currentUserStatus = userService.getUserStatus(userId);
            if (currentUserStatus == UserStatus.CONFIRMED) {
                List<Order> orderList = orderService.findUsersOrders(userId);
                orderList.sort(new Comparator<Order>() {
                    @Override
                    public int compare(Order o1, Order o2) {
                        return o1.getStatus().ordinal() - o2.getStatus().ordinal();
                    }
                });
                session.setAttribute(ParameterAttributeType.ORDER_LIST, orderList);
                session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.MY_ORDERS);
                router = new Router(PagePath.MY_ORDERS, Router.RouterType.REDIRECT);
            } else {

                session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_MAIN_PAGE);
                request.setAttribute(ParameterAttributeType.MESSAGE, KEY_STATUS_ARCHIVED);
                router = new Router(PagePath.TO_MAIN_PAGE, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
