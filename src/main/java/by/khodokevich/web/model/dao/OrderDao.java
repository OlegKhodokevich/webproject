package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.Specialization;
import by.khodokevich.web.exception.DaoException;

import java.util.List;

public interface OrderDao {

    List<Order> findConfirmedUserOrders() throws DaoException;
    boolean setOrderStatus(long idOrder, OrderStatus orderStatus) throws DaoException;
    List<Order> findUserOrders(long idUser) throws DaoException;
    List<Order> findOrdersBySpecialization(Specialization specialization) throws DaoException;
}
