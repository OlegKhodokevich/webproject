package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.REDIRECT;

public class FindOrderInfoDetailsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrderInfoDetailsCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindOrderInfoDetailsCommand.");
        String orderIdString = request.getParameter(ParameterAttributeType.ORDER_ID);
        Router router;
        long orderId;
        try {
            orderId = Long.parseLong(orderIdString);
            OrderService orderService = ServiceProvider.ORDER_SERVICE;
            Optional<Order> optionalOrder = orderService.findDefineOrder(orderId);

            if (optionalOrder.isPresent()) {
                long userId = optionalOrder.get().getUserId();
                UserService userService = ServiceProvider.USER_SERVICE;
                Optional<User> optionalUser = userService.findDefineUser(userId);

                if (optionalUser.isPresent() && orderService.checkOrder(optionalOrder.get())) {
                    String firstName = optionalUser.get().getFirstName();
                    String lastName = optionalUser.get().getLastName();
                    String phone = optionalUser.get().getPhone();
                    String eMail = optionalUser.get().getEMail();
                    HttpSession session = request.getSession();
                    session.setAttribute(FIRST_NAME, firstName);
                    session.setAttribute(LAST_NAME, lastName);
                    session.setAttribute(PHONE, phone);
                    session.setAttribute(E_MAIL, eMail);
                    session.setAttribute(ORDER, optionalOrder.get());
                    router = new Router(PagePath.ORDER_INFO, Router.RouterType.REDIRECT);
                } else {
                    logger.warn("User hasn't found. Order's id = " + orderId);
                    router = new Router(PagePath.ORDERS + "?message=" + ORDERS_NOT_FOUND, Router.RouterType.REDIRECT);
                }

            } else {
                logger.warn("Order hasn't found. Order's id = " + orderId);
                router = new Router(PagePath.ORDERS + "?message=" + ORDERS_NOT_FOUND, Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't find order with id = ", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("Can't parse order id.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
