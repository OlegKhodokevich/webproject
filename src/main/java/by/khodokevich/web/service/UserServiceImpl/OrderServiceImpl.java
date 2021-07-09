package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.dao.OrderDao;
import by.khodokevich.web.dao.impl.OrderDaoImpl;
import by.khodokevich.web.entity.Order;
import by.khodokevich.web.entity.OrderStatus;
import by.khodokevich.web.entity.Specialization;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.OrderService;
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<Order> findAllOrder() throws ServiceException {
        logger.info("Start findAllOrder().");
        List<Order> orders;
        EntityTransaction transaction = new EntityTransaction();
        AbstractDao orderDao = new OrderDaoImpl();
        try {
            transaction.beginSingleQuery(orderDao);
            List<Order> foundedOrders = orderDao.findAll();
            orders = foundedOrders.stream()
                    .filter((s) -> s.getStatus() == OrderStatus.OPEN)
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.endSingleQuery();
            } catch (DaoException e) {
                logger.error("Can't end transaction", e);
            }
        }
        return orders;
    }

    @Override
    public Optional<Order> findDefineOrder(long orderId) throws ServiceException {
        logger.info("Start findDefineOrder(long orderId). orderId = " + orderId);
        Optional<Order> orderOptional;
        EntityTransaction transaction = new EntityTransaction();
        AbstractDao orderDao = new OrderDaoImpl();
        try {
            transaction.beginSingleQuery(orderDao);
            orderOptional = orderDao.findEntityById(orderId);
            if (orderOptional.isPresent() && orderOptional.get().getStatus() != OrderStatus.OPEN) {
                orderOptional = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.endSingleQuery();
            } catch (DaoException e) {
                logger.error("Can't end transaction", e);
            }
        }
        logger.info("End findDefineOrder(long orderId). Order = " + orderOptional);
        return orderOptional;
    }

    @Override
    public List<Order> findUsersOrders(long idUser) throws ServiceException {
        logger.info("Start findUsersOrders(long idUser). IdUser = " + idUser);
        List<Order> orders;
        EntityTransaction transaction = new EntityTransaction();
        AbstractDao orderDao = new OrderDaoImpl();
        try {
            transaction.beginSingleQuery(orderDao);
            orders = ((OrderDao) orderDao).findUserOrders(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.endSingleQuery();
            } catch (DaoException e) {
                logger.error("Can't end transaction", e);
            }
        }
        logger.info("End findUsersOrders(long idUser). Orders = " + orders);
        return orders;
    }

    @Override
    public List<Order> findOrdersBySpecializations(List<Specialization> specializations) throws ServiceException {
        logger.info("Start findOrdersBySpecializations(List<Specialization> specializations). Specializations = " + specializations);
        List<Order> orders = new ArrayList<>();
        EntityTransaction transaction = new EntityTransaction();
        AbstractDao orderDao = new OrderDaoImpl();
        try {
            transaction.beginSingleQuery(orderDao);
            for (int i = 0; i < specializations.size(); i++) {
                List<Order> transferList = ((OrderDao) orderDao).findOrdersBySpecialization(specializations.get(i));

                orders.addAll(transferList.stream()
                        .filter((s) -> s.getStatus() == OrderStatus.OPEN)
                        .toList());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.endSingleQuery();
            } catch (DaoException e) {
                logger.error("Can't end transaction", e);
            }
        }
        logger.info("End findOrdersBySpecializations. Orders = " + orders);
        return orders;
    }
}
