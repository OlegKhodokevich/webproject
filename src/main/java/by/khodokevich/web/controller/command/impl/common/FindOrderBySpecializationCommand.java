package by.khodokevich.web.controller.command.impl.common;

import by.khodokevich.web.controller.command.Command;
import by.khodokevich.web.controller.command.PagePath;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.controller.command.Router;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.khodokevich.web.controller.command.InformationMessage.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.Router.RouterType.*;

public class FindOrderBySpecializationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrderBySpecializationCommand.class);
    private static final String CHECKBOX_VALUE = "on";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindOrdersBySpecializationCommand.");
        List<Order> orderList;
        List<Specialization> specializations = new ArrayList<>();
        Router router;
        HttpSession session = request.getSession();
        try {
            Map<String, Specialization> specializationMap = Specialization.getSpecializationMap();
            Set<String> keys = specializationMap.keySet();
            for (String key : keys) {
                logger.debug("key = |" + key + "| ,value =" + request.getParameter(key));
                String value = request.getParameter(key);
                if (value != null && value.equals(CHECKBOX_VALUE)) {
                    specializations.add(specializationMap.get(key));
                    session.setAttribute(key, "on");
                } else {
                    session.setAttribute(key, "");
                }
            }

            int currentIndexPage;
            String currentIndexPageString = request.getParameter(INDEX_PAGE);
            if (currentIndexPageString == null || currentIndexPageString.isEmpty()) {
                currentIndexPage = 1;
            } else {
                currentIndexPage = Integer.parseInt(currentIndexPageString);
            }
            Pagination pagination = new Pagination(currentIndexPage);
            logger.debug("Specs = " + specializations);
            OrderService orderService = ServiceProvider.ORDER_SERVICE;

            if (!specializations.isEmpty()) {
                orderList = orderService.findOrdersBySpecializations(specializations);
            } else {
                orderList = orderService.findAllOpenOrderOnPage(pagination);
            }

            logger.info("Orders = " + orderList);
            if (orderList.isEmpty()) {
                session.setAttribute(ParameterAttributeType.MESSAGE, ORDERS_NOT_FOUND);
            }
            session.setAttribute(ParameterAttributeType.ORDER_LIST, orderList);
            router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Error during query", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        } catch (NumberFormatException e) {
            logger.error("Can't parse id.");
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }

        return router;
    }
}
