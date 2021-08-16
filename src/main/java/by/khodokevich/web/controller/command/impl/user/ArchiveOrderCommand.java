package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

/**
 * This class archived order.
 * It set close order status.
 */
public class ArchiveOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ArchiveOrderCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start ArchiveOrderCommand.");
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
        HttpSession session = request.getSession();
        String userIdString;
        String activeUserRole = (String)session.getAttribute(ACTIVE_USER_ROLE);
        if (activeUserRole != null && activeUserRole .equalsIgnoreCase("ADMIN")) {
            userIdString = request.getParameter(USER_ID);
        } else {
            userIdString = (String) session.getAttribute(ACTIVE_USER_ID);
        }
        try {
            String orderIdString =  request.getParameter(ORDER_ID);
            if (userIdString != null && orderIdString != null) {
                long orderId = Long.parseLong(orderIdString);
                if (!orderService.setStatus(orderId, OrderStatus.CLOSE)) {
                    logger.error("User Id or Order Id is null. User Id = " + userIdString + " , order id = " + orderIdString);
                    router = new Router(PagePath.ERROR_PAGE, REDIRECT);
                } else {
                    logger.error("Can't change order's status.");
                    router = new Router(PagePath.TO_USERS_ORDERS + "&userId=" + userIdString, REDIRECT);
                }
            } else{
                logger.error("User Id or Order Id is null. User Id = " + userIdString + " , order id = " + orderIdString);
                router = new Router(PagePath.ERROR_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error("Can't change order's status.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("Can't parse id.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
