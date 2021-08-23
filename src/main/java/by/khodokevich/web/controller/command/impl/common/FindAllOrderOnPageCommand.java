package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.InformationMessage.*;

/**
 * This class search all order on page.
 */
public class FindAllOrderOnPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindAllOrderOnPageCommand.class);
    private static final String CHECKBOX_EMPTY_VALUE = "";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            Map<String, Specialization> specializationMap = Specialization.getSpecializationMap();
            Set<String> keys = specializationMap.keySet();
            HttpSession session = request.getSession();
            for (String key : keys) {
                session.setAttribute(key, CHECKBOX_EMPTY_VALUE);
            }
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

            if (orderList.isEmpty()) {
                session.setAttribute(MESSAGE, ORDERS_NOT_FOUND);
            }
            session.setAttribute(PAGINATION, pagination);
            logger.debug("Pagination " + pagination.getListVisiblePage());
            session.setAttribute(ORDER_LIST, orderList);
            session.removeAttribute(REASON);
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
