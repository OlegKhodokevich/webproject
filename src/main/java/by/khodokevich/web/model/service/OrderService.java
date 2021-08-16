package by.khodokevich.web.model.service;

import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface serve operations with orders.
 */
public interface OrderService {
    /**
     * Method searches all open order.
     * Number of items is limited by pagination.
     *
     * @param pagination information about pagination
     * @return List of order
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Order> findAllOpenOrderOnPage(Pagination pagination) throws ServiceException;
    /**
     * Method searches order by id.
     *
     * @param orderId of order
     * @return optional order
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    Optional<Order> findDefineOrder(long orderId) throws ServiceException;

    /**
     * Method searches all user's orders by user's id.
     *
     * @param idUser of user who create order
     * @return List of orders
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Order> findUsersOrders(long idUser) throws ServiceException;

    /**
     * Method archive expired user's orders which completion date is over.
     * Status of order will set close.
     *
     * @param initialList of orders which is archived.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    void archiveExpiredUsersOrders(List<Order> initialList) throws ServiceException;

    /**
     * Method check order if it is expired (completion date is over).
     * Status of order will set close.
     *
     * @param order which is checked.
     * @return true if operation is successful and false if status must be changed to close, but it can't be done
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean checkOrder(Order order) throws ServiceException;

    /**
     * Method search open and not expired orders by list of specializations.
     *
     * @param specializations list of specializations.
     * @return list of orders
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    List<Order> findOrdersBySpecializations(List<Specialization> specializations) throws ServiceException;

    /**
     * Method create order.
     *
     * @param orderData Map with order information.
     * @return Map with result operation and correct value of order information if one of the params is incorrect.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    Map<String, String> createOrder(Map<String, String> orderData) throws ServiceException;

    /**
     * Method update order.
     *
     * @param orderData Map with order information.
     * @return Map with result operation and correct value of order information if one of the params is incorrect.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    Map<String, String> updateOrder(Map<String, String> orderData) throws ServiceException;

    /**
     * Method set status.
     *
     * @param orderId of order.
     * @param status of order.
     * @return true if it is updated, in other way will return false.
     * @throws ServiceException if query can't be executed  or connection isn't work
     */
    boolean setStatus(long orderId, OrderStatus status) throws ServiceException;

}
