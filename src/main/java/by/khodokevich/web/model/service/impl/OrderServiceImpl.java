package by.khodokevich.web.model.service.impl;

import by.khodokevich.web.model.builder.OrderBuilder;
import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.OrderDaoImpl;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.OrderService;
import by.khodokevich.web.util.validator.OrderDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

/**
 * Class implement Order Service and serve operations with orders.
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private static OrderService instance;

    private static final int NUMBER_ITEMS_ON_PAGE = 3;
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private OrderServiceImpl() {
    }
    /**
     * @return OrderServiceImpl instance as singleton
     */
    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    /**
     * Method searches all open order.
     * Number of items is limited by pagination.
     *
     * @param pagination information about pagination
     * @return List of order
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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

    /**
     * Method searches order by id.
     *
     * @param orderId of order
     * @return optional order
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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

    /**
     * Method searches all user's orders by user's id.
     *
     * @param idUser of user who create order
     * @return List of orders
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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

    /**
     * Method archive expired user's orders which completion date is over.
     * Status of order will set close.
     *
     * @param initialList of orders which is archived.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public void archiveExpiredUsersOrders(List<Order> initialList) throws ServiceException {
        logger.info("Start archiveExpiredUsersOrders(List<Order> initialList). InitialList  = " + initialList);
        Date currentDate = new Date();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            for (Order order : initialList) {
                if (order.getCompletionDate().before(currentDate)) {

                    if (orderDao.setOrderStatus(order.getOrderId(), OrderStatus.CLOSE)) {
                        order.closeOrder();
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End archiveExpiredUsersOrders(List<Order> initialList). Orders = " + initialList);
    }

    /**
     * Method check order if it is expired (completion date is over).
     * Status of order will set close.
     *
     * @param order which is checked.
     * @return true if operation is successful and false if status must be changed to close, but it can't be done
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public boolean checkOrder(Order order) throws ServiceException {
        logger.info("Start checkOrder(Order order). Order  = " + order);
        boolean result = true;
        Date currentDate = new Date();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);
            if (order.getStatus() == OrderStatus.OPEN && order.getCompletionDate().before(currentDate)) {
                result = orderDao.setOrderStatus(order.getOrderId(), OrderStatus.CLOSE);
                if (result) {
                    order.closeOrder();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End archiveExpiredUsersOrders(Order order). Order = " + order);
        return result;
    }

    /**
     * Method search open and not expired orders by list of specializations.
     *
     * @param specializations list of specializations.
     * @return list of orders
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    @Override
    public List<Order> findOrdersBySpecializations(List<Specialization> specializations) throws ServiceException {
        logger.info("Start findOrdersBySpecializations(List<Specialization> specializations). Specializations = " + specializations);
        List<Order> orders = new ArrayList<>();
        try (EntityTransaction transaction = new EntityTransaction()) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            transaction.beginSingleQuery(orderDao);

            for (Specialization specialization : specializations) {
                List<Order> transferList = orderDao.findOpenOrdersBySpecialization(specialization);
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

    /**
     * Method create order.
     *
     * @param orderData Map with order information.
     * @return Map with result operation and correct value of order information if one of the params is incorrect.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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

    /**
     * Method update order.
     *
     * @param orderData Map with order information.
     * @return Map with result operation and correct value of order information if one of the params is incorrect.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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

    /**
     * Method set status.
     *
     * @param orderId of order.
     * @param status of order.
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
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
