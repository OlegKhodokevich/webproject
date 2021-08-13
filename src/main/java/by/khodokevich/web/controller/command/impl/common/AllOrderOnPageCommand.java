package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.InformationMessage.*;

public class AllOrderOnPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AllOrderOnPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            int currentIndexPage;
            String currentIndexPageString = request.getParameter(INDEX_PAGE);
            if (currentIndexPageString == null || currentIndexPageString.isEmpty()) {
                currentIndexPage = 1;
            } else {
                currentIndexPage = Integer.parseInt(currentIndexPageString);
            }

            Pagination pagination = new Pagination(currentIndexPage);
            OrderService orderService = ServiceProvider.ORDER_SERVICE;
            List<Order> orderList = orderService.findAllOpenOrderOnPage(pagination);

            HttpSession session = request.getSession();
            if (orderList.isEmpty()) {
                session.setAttribute(MESSAGE, ORDERS_NOT_FOUND);
            }
            session.setAttribute(PAGINATION, pagination);
            logger.debug("Pagination " + pagination.getListVisiblePage());
            session.setAttribute(ORDER_LIST, orderList);
            router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Can't find all orders", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("index of page has incorrect format", e);
            router = new Router(PagePath.ERROR_PAGE, Router.RouterType.REDIRECT);
        }
        return router;
    }
}
