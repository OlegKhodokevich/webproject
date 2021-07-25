package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.builder.OrderBuilder;
import by.khodokevich.web.command.ParameterAttributeType;
import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.dao.OrderDao;
import by.khodokevich.web.dao.impl.OrderDaoImpl;
import by.khodokevich.web.dao.impl.UserDaoImpl;
import by.khodokevich.web.entity.*;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.CheckingResult;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.validator.OrderDataValidator;
import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static by.khodokevich.web.command.ParameterAttributeType.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    protected OrderServiceImpl() {
    }

    @Override
    public List<Order> findAllOrder() throws ServiceException {
        logger.info("Start findAllOrder().");
        List<Order> orders;
        AbstractDao orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginSingleQuery(orderDao);
            List<Order> foundedOrders = orderDao.findAll();
            orders = foundedOrders.stream()
                    .filter((s) -> s.getStatus() == OrderStatus.OPEN)
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findDefineOrder(long orderId) throws ServiceException {
        logger.info("Start findDefineOrder(long orderId). orderId = " + orderId);
        Optional<Order> orderOptional;
        AbstractDao orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginSingleQuery(orderDao);
            orderOptional = orderDao.findEntityById(orderId);
            if (orderOptional.isPresent() && orderOptional.get().getStatus() != OrderStatus.OPEN) {
                orderOptional = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        logger.info("End findDefineOrder(long orderId). Order = " + orderOptional);
        return orderOptional;
    }

    @Override
    public List<Order> findUsersOrders(long idUser) throws ServiceException {
        logger.info("Start findUsersOrders(long idUser). IdUser = " + idUser);
        List<Order> orders;
        AbstractDao orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginSingleQuery(orderDao);
            orders = ((OrderDao) orderDao).findUserOrders(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        logger.info("End findUsersOrders(long idUser). Orders = " + orders);
        return orders;
    }

    @Override
    public List<Order> findOrdersBySpecializations(List<Specialization> specializations) throws ServiceException {
        logger.info("Start findOrdersBySpecializations(List<Specialization> specializations). Specializations = " + specializations);
        List<Order> orders = new ArrayList<>();
        AbstractDao orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction();) {
            transaction.beginSingleQuery(orderDao);
            for (int i = 0; i < specializations.size(); i++) {
                List<Order> transferList = ((OrderDao) orderDao).findOrdersBySpecialization(specializations.get(i));

                orders.addAll(transferList.stream()
                        .filter((s) -> s.getStatus() == OrderStatus.OPEN)
                        .toList());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        logger.info("End findOrdersBySpecializations. Orders = " + orders);
        return orders;
    }

    @Override
    public Map<String, String> createOrder(Map<String, String> orderData){
        Map<String, String> answerMap = OrderDataValidator.checkOrderData(orderData);
        String operationResult = answerMap.get(RESULT);
        if (operationResult.equalsIgnoreCase(CheckingResult.SUCCESS.name())) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            UserDaoImpl userDao = new UserDaoImpl();
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            String completionDateString = null;
            String specializationString = null;
            try (EntityTransaction transaction = new EntityTransaction()){
                transaction.begin(orderDao, userDao);
                long userId = Long.parseLong(orderData.get(ParameterAttributeType.USER_ID));
                Optional<User> user = userDao.findEntityById(userId);
                if (user.isPresent() && user.get().getStatus() == UserStatus.CONFIRMED) {
                    String title =orderData.get(TITLE);
                    String description =orderData.get(DESCRIPTION);
                    String address =orderData.get(ADDRESS);
                    completionDateString =orderData.get(COMPLETION_DATE);
                    Date completionDate = dateFormat.parse(completionDateString);
                    specializationString =orderData.get(SPECIALIZATION);
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
                            .buildOrder();
                    logger.info("Has found next order = " + order);
                    boolean isCreate = orderDao.create(order);
                    if (isCreate) {
                        answerMap.put(RESULT, CheckingResult.SUCCESS.toString());
                    } else {
                        answerMap.put(RESULT, CheckingResult.ERROR.toString());
                    }
                } else {
                    answerMap.put("result", CheckingResult.USER_UNKNOWN.toString());
                }
            } catch (ParseException e) {
                answerMap.put(RESULT, CheckingResult.ERROR.toString());
                logger.error("Can't parse completionDate" + completionDateString,e);
            } catch (IllegalArgumentException e) {
                answerMap.put(RESULT, CheckingResult.ERROR.toString());
                logger.error("Can't find enum element specialization" + specializationString,e);
            } catch (DaoException e) {
                answerMap.put(RESULT, CheckingResult.ERROR.toString());
                logger.error("Can't create order.",e);
            } catch (Exception e) {
                answerMap.put(RESULT, CheckingResult.ERROR.toString());
                logger.error("Can't make transaction.",e);
            }
        }else {
            answerMap.put(RESULT, CheckingResult.NOT_VALID.toString());
        }
        return answerMap;
    }

    @Override
    public boolean setStatus(long orderId, OrderStatus status) throws ServiceException {
        logger.info("Start setStatus()");
        boolean resultOperation;
        OrderDaoImpl orderDao = new OrderDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()) {
            transaction.beginSingleQuery(orderDao);
            resultOperation = orderDao.setOrderStatus(orderId, status);

        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            logger.error("Can't creat order.",e);
            throw new ServiceException("Error of closing transaction.", e);
        }
        return resultOperation;
    }
}
