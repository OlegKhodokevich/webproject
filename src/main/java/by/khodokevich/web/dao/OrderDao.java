package by.khodokevich.web.dao;

import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.OrderStatus;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.DaoException;

import java.util.List;

public interface OrderDao {
    boolean setOrderStatus(long idOrder, OrderStatus orderStatus) throws DaoException;
    List<Order> findUserOrders(long idUser) throws DaoException;
    List<Order> findOrdersBySpecialization(Specialization specialization) throws DaoException;;
}
