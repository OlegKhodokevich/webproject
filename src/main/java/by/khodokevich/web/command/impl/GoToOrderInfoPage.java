package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoToOrderInfoPage implements Command {
    private static final Logger logger = LogManager.getLogger(GoToOrderInfoPage.class);
    private static final String ORDER_NOT_FOUND = "order.order_not_found";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start GoToOrderInfoPage");
        HttpSession session = request.getSession();
        session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_REGISTER_PAGE);
        long orderId = Long.parseLong(request.getParameter(ParameterAttributeType.ORDER_ID));
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        Router router;
        try {
            Optional<Order> optionalOrder = orderService.findDefineOrder(orderId);
            if (optionalOrder.isPresent()){
                long userId = optionalOrder.get().getUserId();
                UserService userService = ServiceProvider.USER_SERVICE;
                Optional<User> optionalUser =   userService.findDefineUser(userId);
                if (optionalUser.isPresent()) {
                    String firstName = optionalUser.get().getfirstName();
                    String lastName = optionalUser.get().getfirstName();
                    String phone = optionalUser.get().getPhone();
                    session.setAttribute(ParameterAttributeType.FIRST_NAME, firstName);
                    session.setAttribute(ParameterAttributeType.LAST_NAME, lastName);
                    session.setAttribute(ParameterAttributeType.PHONE, phone);
                    session.setAttribute(ParameterAttributeType.ORDER, optionalOrder.get());
                    session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_ORDER_INFO);
                    router = new Router(PagePath.ORDER_INFO, Router.RouterType.REDIRECT);
                } else {
                    logger.warn("User hasn't found. Order's id = " + orderId);
//                    request.setAttribute(ParameterAttributeType.MESSAGE, ORDER_NOT_FOUND);
                    session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_ORDERS);
                    router = new Router(PagePath.ORDERS + "?message=" + ORDER_NOT_FOUND, Router.RouterType.REDIRECT);
                }
            } else {
                logger.warn("Order hasn't found. Order's id = " + orderId);
//                request.setAttribute(ParameterAttributeType.MESSAGE, ORDER_NOT_FOUND);
                session.setAttribute(ParameterAttributeType.CURRENT_PAGE, PagePath.TO_ORDERS);
                router = new Router(PagePath.ORDERS + "?message=" + ORDER_NOT_FOUND, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't find order with id = " + orderId, e);
            router = new Router(PagePath.ORDERS + "?message=" + ORDER_NOT_FOUND, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
