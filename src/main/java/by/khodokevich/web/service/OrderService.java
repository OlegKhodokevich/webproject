package by.khodokevich.web.service;

import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.ServiceException;
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder() throws ServiceException;
    List<Order> findUsersOrders(long idUser) throws ServiceException;
    List<Order> findOrdersBySpecializations(List<Specialization> specializationsString) throws ServiceException;
}
