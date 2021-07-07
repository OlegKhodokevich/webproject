package by.khodokevich.web.dao;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.dao.impl.OrderDaoImpl;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.OrderStatus;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.PoolConnectionException;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class OrderDaoMain {
//    public static void main(String[] args) {
//        AbstractDao orderDaoImpl = new OrderDaoImpl();
//
//        try {
//            Connection connection = CustomConnectionPool.getInstance().getConnection();
//            orderDaoImpl.setConnection(connection);
//            List<Order> orders= orderDaoImpl.findAll();
//            System.out.println(orders);
//            Optional<Order> order = orderDaoImpl.findEntityById(8);
////            System.out.println(orderDao.delete(order.get()));
////            System.out.println(orderDao.delete(10));
//            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
//            Order orderInsert = new Order(12,5, "Destroy3", "destroy toilet", "Minsk Bedi st.", parser.parse("2021-08-16"), Specialization.WALLPAPERING, OrderStatus.UNDER_CONSIDERATION);
////            orderDao.create(orderInsert);
//            orderDaoImpl.update(orderInsert);
//            System.out.println(orderDaoImpl.update(orderInsert));
//        } catch (DaoException e) {
//            e.printStackTrace();
//        } catch (PoolConnectionException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
