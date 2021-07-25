package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserServiceImpl.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.khodokevich.web.command.ParameterAttributeType.*;

public class FindOrdersBySpecializationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindOrdersBySpecializationCommand.class);
    private static final String ORDERS_NOT_FOUND = "order.empty_list";
    private static final String CHECKBOX_VALUE = "on";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindOrdersBySpecializationCommand.");
        List<Order> orderList;
        List<Specialization> specializations = new ArrayList<>();
        Router router;
        OrderService orderService = ServiceProvider.ORDER_SERVICE;
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
            logger.debug("Specs = " + specializations);
            if (!specializations.isEmpty()) {
                orderList = orderService.findOrdersBySpecializations(specializations);
            } else {
                orderList = orderService.findAllOrder();
            }
            logger.info("Orders = " + orderList);
            if (!orderList.isEmpty()) {
//                request.setAttribute(ParameterAndAttributeType.ORDER_LIST, orderList);

                session.setAttribute(ParameterAttributeType.ORDER_LIST, orderList);
                router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);
            } else {
                orderList = Collections.EMPTY_LIST;
                session.setAttribute(ParameterAttributeType.MESSAGE, ORDERS_NOT_FOUND);
                router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);
                session.setAttribute(ParameterAttributeType.ORDER_LIST, orderList);
            }

        } catch (ServiceException e) {
            logger.error("Error during query", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
