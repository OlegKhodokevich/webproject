package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.model.builder.OrderBuilder;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.OrderDaoImpl;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.validator.OrderDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private static OrderService instance;

    private static final int NUMBER_ITEMS_ON_PAGE = 3;
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findAllOpenOrderOnPage(Pagination pagination) throws ServiceException {
        logger.info("Start findAllOrderOnPage(Pagination pagination).");
        List<Order> orders;
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.begin(orderDao);
            int numberItems = orderDao.findNumberItems();
            pagination.setNumberItems(numberItems);
            pagination.setOnePageNumberItems(NUMBER_ITEMS_ON_PAGE);
            orders = orderDao.findAllOpenOrdersOnPage(pagination);
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findOpenOrderConfirmedUsers() throws ServiceException {
        logger.info("Start findOpenOrderConfirmedUsers().");
        List<Order> orders;
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            List<Order> foundedOrders = orderDao.findConfirmedUserOrders();
            Date currentDate = new Date();
            orders = foundedOrders.stream()
                    .filter((s) -> s.getStatus() == OrderStatus.OPEN && !s.getCompletionDate().before(currentDate))
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findDefineOrder(long orderId) throws ServiceException {
        logger.info("Start findDefineOrder(long orderId). orderId = " + orderId);
        Optional<Order> orderOptional;
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            orderOptional = orderDao.findEntityById(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findDefineOrder(long orderId). Order = " + orderOptional);
        return orderOptional;
    }

    @Override
    public List<Order> findUsersOrders(long idUser) throws ServiceException {
        logger.info("Start findUsersOrders(long idUser). IdUser = " + idUser);
        List<Order> orders;
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            orders = orderDao.findUserOrders(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findUsersOrders(long idUser). Orders = " + orders);
        return orders;
    }

    @Override
    public void archiveExpiredUsersOrders(List<Order> orderlList) throws ServiceException {
        logger.info("Start archiveExpiredUsersOrders(List<Order> initialList). InitialList  = " + orderlList);
        Date currentDate = new Date();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            for (Order order : orderlList) {
                if (order.getCompletionDate().before(currentDate)) {

                    if (orderDao.setOrderStatus(order.getOrderId(), OrderStatus.CLOSE)) {
                        order.setStatus(OrderStatus.CLOSE);
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End archiveExpiredUsersOrders(List<Order> initialList). Orders = " + orderlList);
    }

    @Override
    public void archiveExpiredUsersOrders(Order order) throws ServiceException {
        logger.info("Start archiveExpiredUsersOrders(Order order). Order  = " + order);
        Date currentDate = new Date();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            if (order.getStatus() == OrderStatus.OPEN && order.getCompletionDate().before(currentDate)) {

                if (orderDao.setOrderStatus(order.getOrderId(), OrderStatus.CLOSE)) {
                    order.setStatus(OrderStatus.CLOSE);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End archiveExpiredUsersOrders(Order order). Order = " + order);
    }

    @Override
    public List<Order> findOrdersBySpecializations(List<Specialization> specializations) throws ServiceException {
        logger.info("Start findOrdersBySpecializations(List<Specialization> specializations). Specializations = " + specializations);
        List<Order> orders = new ArrayList<>();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);

            for (Specialization specialization : specializations) {
                List<Order> transferList = orderDao.findOrdersBySpecialization(specialization);
                Date currentDate = new Date();
                orders.addAll(transferList.stream()
                        .filter((s) -> s.getStatus() == OrderStatus.OPEN && !s.getCompletionDate().before(currentDate))
                        .toList());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findOrdersBySpecializations. Orders = " + orders);
        return orders;
    }

    @Override
    public Map<String, String> createOrder(Map<String, String> orderData) throws ServiceException {
        Map<String, String> answerMap = OrderDataValidator.checkOrderData(orderData);
        String operationResult = answerMap.get(RESULT);
        if (operationResult.equalsIgnoreCase(CheckingResult.SUCCESS.name())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            String completionDateString = null;
            String specializationString = null;
            try (EntityTransaction transaction = new EntityTransaction()) {
                OrderDaoImpl orderDao = new OrderDaoImpl();
                transaction.beginSingleQuery(orderDao);
                long userId = Long.parseLong(orderData.get(ParameterAttributeType.USER_ID));
                String title = orderData.get(TITLE);
                String description = orderData.get(DESCRIPTION);
                String address = orderData.get(ADDRESS);
                completionDateString = orderData.get(COMPLETION_DATE);
                Date completionDate = dateFormat.parse(completionDateString);
                specializationString = orderData.get(SPECIALIZATION);
                Specialization specialization = Specialization.valueOf(specializationString.toUpperCase());

                Date creationDate = new Date();
                OrderStatus status = OrderStatus.OPEN;
                Order order = new OrderBuilder()
                        .userId(userId)
                        .title(title)
                        .description(description)
                        .address(address)
                        .creationDate(creationDate)
                        .completionDate(completionDate)
                        .specialization(specialization)
                        .status(status)
                        .buildOrderWithoutId();
                logger.info("Prepare to create next order = " + order);
                boolean isCreate = orderDao.create(order);
                if (isCreate) {
                    answerMap.put(RESULT, CheckingResult.SUCCESS.toString());
                } else {
                    answerMap.put(RESULT, CheckingResult.ERROR.toString());
                }
            } catch (ParseException e) {
                logger.error("Can't parse completionDate" + completionDateString, e);
                throw new ServiceException("Can't parse completionDate.", e);
            } catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            } catch (IllegalArgumentException e) {
                logger.error("Can't find enum element specialization" + specializationString, e);
                throw new ServiceException("Can't find enum element specialization.", e);
            } catch (DaoException e) {
                logger.error("Can't create order.", e);
                throw new ServiceException("Can't create order.", e);
            }
        } else {
            answerMap.put(RESULT, CheckingResult.NOT_VALID.toString());
        }
        return answerMap;
    }

    @Override
    public Map<String, String> updateOrder(Map<String, String> orderData) throws ServiceException {
        logger.info("Start updateOrder(Map<String, String> orderData)." + orderData);
        Map<String, String> answerMap = OrderDataValidator.checkOrderData(orderData);
        String operationResult = answerMap.get(RESULT);
        if (operationResult.equalsIgnoreCase(CheckingResult.SUCCESS.name())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            String completionDateString = null;
            String specializationString = null;
            try (EntityTransaction transaction = new EntityTransaction()) {
                OrderDaoImpl orderDao = new OrderDaoImpl();
                transaction.beginSingleQuery(orderDao);
                long userId = Long.parseLong(orderData.get(ParameterAttributeType.USER_ID));
                long orderId = Long.parseLong(orderData.get(ORDER_ID));
                String title = orderData.get(TITLE);
                String description = orderData.get(DESCRIPTION);
                String address = orderData.get(ADDRESS);
                completionDateString = orderData.get(COMPLETION_DATE);
                Date completionDate = dateFormat.parse(completionDateString);
                specializationString = orderData.get(SPECIALIZATION);
                Specialization specialization = Specialization.valueOf(specializationString.toUpperCase());

                Date creationDate = new Date();
                OrderStatus status = OrderStatus.OPEN;
                Order order = new OrderBuilder()
                        .orderId(orderId)
                        .userId(userId)
                        .title(title)
                        .description(description)
                        .address(address)
                        .creationDate(creationDate)
                        .completionDate(completionDate)
                        .specialization(specialization)
                        .status(status)
                        .buildOrder();

                logger.info("Prepare to update next order = " + order);
                boolean isUpdate = orderDao.update(order);
                if (isUpdate) {
                    answerMap.put(RESULT, CheckingResult.SUCCESS.toString());
                } else {
                    answerMap.put(RESULT, CheckingResult.ERROR.toString());
                }

            } catch (ParseException e) {
                logger.error("Can't parse completionDate" + completionDateString, e);
                throw new ServiceException("Can't parse completionDate.", e);
            } catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            } catch (IllegalArgumentException e) {
                logger.error("Can't find enum element specialization" + specializationString, e);
                throw new ServiceException("Can't find enum element specialization.", e);
            } catch (DaoException e) {
                logger.error("Can't update order.", e);
                throw new ServiceException("Can't update order.", e);
            }
        }
        return answerMap;
    }

    @Override
    public boolean setStatus(long orderId, OrderStatus status) throws ServiceException {
        logger.info("Start setStatus()");
        boolean resultOperation;
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            resultOperation = orderDao.setOrderStatus(orderId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultOperation;
    }
}
