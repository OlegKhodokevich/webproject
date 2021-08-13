package by.khodokevich.web.controller.command.impl.admin;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import by.khodokevich.web.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

public class SetUserStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetUserStatusCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start SetUserStatusCommand");
        Router router;
        try {
            String statusString = request.getParameter(STATUS_COMMAND);
            UserStatus status = UserStatus.valueOf(statusString);
            String userIdString = request.getParameter(USER_ID);

            if (statusString != null) {
                long userId = Long.parseLong(userIdString);
                UserService userService = ServiceProvider.USER_SERVICE;
                boolean resultCommand = userService.changeUserStatus(userId, status);

                if (resultCommand) {
                    HttpSession session = request.getSession();
                    List<User> userList = (List<User>) session.getAttribute(USER_LIST);
                    String indexString = request.getParameter(INDEX);
                    int index = Integer.parseInt(indexString);
                    User user = userList.get(index);
                    user.setStatus(status);
                    OrderService orderService = ServiceProvider.ORDER_SERVICE;
                    List<Order> orders = orderService.findUsersOrders(userId);

                    for (Order order : orders) {
                        if (order.getStatus() == OrderStatus.OPEN) {
                            if (!orderService.setStatus(order.getOrderId(), OrderStatus.CLOSE)) {
                                logger.error("Can't change order status. Order id = " + order.getOrderId());
                            }
                        }
                    }

                    if (status != UserStatus.CONFIRMED) {
                        Set<Long> archivedUserSet = (Set<Long>) (request).getServletContext().getAttribute(SET_ARCHIVE_USERS);
                        archivedUserSet.add(userId);
                    }
                    router = new Router(PagePath.ALL_USERS, REDIRECT);

                } else {
                    logger.error("Can't change user's status.");
                    router = new Router(PagePath.ERROR_PAGE, REDIRECT);
                }
            } else {
                logger.error("status or user id is null");
                router = new Router(PagePath.ERROR_PAGE, REDIRECT);
            }

        } catch (NumberFormatException e) {
            logger.error("UserId is incorrect. It isn't able to be parsed.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (IllegalArgumentException e) {
            logger.error("UserStatus is incorrect. It isn't able to be parsed.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.error("It is impassible to change status.", e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }
        return router;
    }
}
