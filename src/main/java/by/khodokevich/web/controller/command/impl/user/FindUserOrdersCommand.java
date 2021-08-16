package by.khodokevich.web.controller.command.impl.user;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * This class search customer order.
 */
public class FindUserOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserOrdersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindUserOrdersCommand.");
        Router router;
        try {
            String userIdString = request.getParameter(USER_ID);
            HttpSession session = request.getSession();
            if (userIdString == null || userIdString.isEmpty()) {
                userIdString = String.valueOf(session.getAttribute(ACTIVE_USER_ID));
            }
            long userId = Long.parseLong(userIdString);
            OrderService orderService = ServiceProvider.ORDER_SERVICE;
            List<Order> orderList = orderService.findUsersOrders(userId);
            orderService.archiveExpiredUsersOrders(orderList);
            orderList.sort(Comparator.comparingInt(o -> o.getStatus().getPriority()));
            logger.info(" OrderList = " + orderList);
            session.setAttribute(ParameterAttributeType.ORDER_LIST, orderList);
            router = new Router(PagePath.MY_ORDERS, Router.RouterType.REDIRECT);

        } catch (NumberFormatException e) {
            logger.error("UserId has incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NullPointerException e) {
            logger.error("Active UserId is null", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
