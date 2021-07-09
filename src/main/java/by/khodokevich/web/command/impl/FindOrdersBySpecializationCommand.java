package by.khodokevich.web.command.impl;

import by.khodokevich.web.command.Command;
import by.khodokevich.web.command.PagePath;
import by.khodokevich.web.command.ParameterAndAttributeType;
import by.khodokevich.web.command.Router;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserServiceImpl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindOrdersBySpecializationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String ORDERS_NOT_FOUND = "order.empty_list";
    private static final String CHECKBOX_VALUE = "on";

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Start FindOrdersBySpecializationCommand.");
        List<Specialization> specializations = new ArrayList<>();
        Router router;
        logger.info(request.getParameter("spec3"));
        logger.info(request.getAttributeNames());
        logger.info("key = spec3 ,value =" + request.getAttribute("spec3"));
        OrderService orderService = new OrderServiceImpl();
        try {

            Map<String, Specialization> specializationMap = Specialization.getSpecializationMap();
            Set<String> keys = specializationMap.keySet();
            for (String key : keys) {
                logger.info("key = |" + key + "| ,value =" + request.getParameter(key));
                String value = request.getParameter(key);
                if (value != null && value.equals(CHECKBOX_VALUE)) {
                    specializations.add(specializationMap.get(key));
                }
            }
            logger.debug("Specs = " + specializations);
            List<Order> orderList = orderService.findOrdersBySpecializations(specializations);
            logger.info("Orders = " + orderList);
            if (!orderList.isEmpty()) {
//                request.setAttribute(ParameterAndAttributeType.ORDER_LIST, orderList);
                HttpSession session = request.getSession();
                session.setAttribute(ParameterAndAttributeType.ORDER_LIST, orderList);
            } else {
                request.setAttribute(ParameterAndAttributeType.MESSAGE, ORDERS_NOT_FOUND);
                request.setAttribute(ParameterAndAttributeType.EMPTY_LIST, Boolean.valueOf(true));
            }
            router = new Router(PagePath.ORDERS, Router.RouterType.REDIRECT);

        } catch (ServiceException e) {
            logger.error("Error during query", e);
            router = new Router(PagePath.TO_ERROR_PAGE, Router.RouterType.REDIRECT);
        }

        return router;
    }
}
