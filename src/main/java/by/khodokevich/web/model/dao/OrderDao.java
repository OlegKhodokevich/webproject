package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.DaoException;

import java.util.List;
/**
 * This interface manage entity order in database.
 * It is used for select create or update information connected with order.
 */
public interface OrderDao {
    /**
     * Method searches all order's entity in database which has open status.
     * Number of items is limited by pagination.
     *
     * @param pagination is information about pagination
     * @return List of orders have been found in database
     * @throws DaoException if can't execute query
     */
    List<Order> findAllOpenOrdersOnPage(Pagination pagination) throws DaoException;

    /**
     * Method searches all open orders which customers are confirmed.
     *
     * @return list of contracts.
     * @throws DaoException if can't execute query
     */
    List<Order> findOpenConfirmedUserOrders() throws DaoException;

    /**
     * Method searches all order's order by customer id.
     *
     * @param idUser of user which order need to find
     * @return List of orders.
     * @throws DaoException if can't execute query
     */
    List<Order> findUserOrders(long idUser) throws DaoException;

    /**
     * Method searches all open orders by specialization.
     *
     * @param specialization of order which need to find
     * @return List of orders.
     * @throws DaoException if can't execute query
     */
    List<Order> findOpenOrdersBySpecialization(Specialization specialization) throws DaoException;

    /**
     * Method set order status using order id.
     *
     * @param idOrder of order
     * @param orderStatus of order
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    boolean setOrderStatus(long idOrder, OrderStatus orderStatus) throws DaoException;

    /**
     * Method find number of open orders in database.
     *
     * @return true if it is updated, in other way will return false.
     * @throws DaoException if can't execute query
     */
    int findNumberItems() throws DaoException;
}
