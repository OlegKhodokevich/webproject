package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAndAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.OrderServiceImpl;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GoToOrderInfoPage implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ORDER_NOT_FOUND = "order.order_not_found";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start GoToOrderInfoPage");
        HttpSession session = request.getSession();
        session.setAttribute(ParameterAndAttributeType.CURRENT_PAGE, PagePath.TO_REGISTER_PAGE);
        long orderId = Long.parseLong(request.getParameter(ParameterAndAttributeType.ORDER_ID));
        OrderService orderService = new OrderServiceImpl();
        try {
            Optional<Order> optionalOrder = orderService.findDefineOrder(orderId);
            if (optionalOrder.isPresent()){
                long userId = optionalOrder.get().getUserId();
                UserService userService = new UserServiceImpl();
                Optional<User> optionalUser =   userService.findDefineUser(userId);
                if (optionalOrder.isPresent()) {
                    String firstName = optionalUser.get().getfirstName();
                    String lastName = optionalUser.get().getfirstName();
                    String phone = optionalUser.get().getPhone();
                    session.setAttribute(ParameterAndAttributeType.FIRST_NAME, firstName);
                    session.setAttribute(ParameterAndAttributeType.LAST_NAME, lastName);
                    session.setAttribute(ParameterAndAttributeType.PHONE, phone);
                    session.setAttribute(ParameterAndAttributeType.ORDER, optionalOrder.get());
                } else {
                    logger.warn("User hasn't found. Order's id = " + orderId);
                    request.setAttribute(ParameterAndAttributeType.MESSAGE, ORDER_NOT_FOUND);
                }
            } else {
                logger.warn("Order hasn't found. Order's id = " + orderId);
                request.setAttribute(ParameterAndAttributeType.MESSAGE, ORDER_NOT_FOUND);
            }

        } catch (ServiceException e) {
            logger.error("Can't find order with id = " + orderId, e);
        }
        Router router = new Router(PagePath.ORDER_INFO, Router.RouterType.REDIRECT);
        session.setAttribute(ParameterAndAttributeType.CURRENT_PAGE, PagePath.ORDERS);
        return router;
    }
}
