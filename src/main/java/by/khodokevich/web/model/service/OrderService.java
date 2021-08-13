package by.khodokevich.web.model.service;

import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllOpenOrderOnPage(Pagination pagination) throws ServiceException;

    List<Order> findOpenOrderConfirmedUsers() throws ServiceException;

    Optional<Order> findDefineOrder(long orderId) throws ServiceException;

    List<Order> findUsersOrders(long idUser) throws ServiceException;

    void archiveExpiredUsersOrders(List<Order> initialList) throws ServiceException;

    void archiveExpiredUsersOrders(Order order) throws ServiceException;

    List<Order> findOrdersBySpecializations(List<Specialization> specializationsString) throws ServiceException;

    Map<String, String> createOrder(Map<String, String> orderData) throws ServiceException;

    Map<String, String> updateOrder(Map<String, String> orderData) throws ServiceException;

    boolean setStatus(long orderId, OrderStatus status) throws ServiceException;

}
