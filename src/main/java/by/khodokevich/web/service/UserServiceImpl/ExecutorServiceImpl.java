package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.dao.ExecutorDao;
import by.khodokevich.web.dao.impl.ExecutorDaoImpl;
import by.khodokevich.web.entity.Executor;
import by.khodokevich.web.entity.OrderStatus;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.ExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExecutorServiceImpl implements ExecutorService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    protected ExecutorServiceImpl() {
    }

    @Override
    public List<Executor> findAllExecutors() throws ServiceException {
        logger.info("Start findAllExecutors().");
        List<Executor> executors;

        AbstractDao executorDao = new ExecutorDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(executorDao);
            List<Executor> foundedExecutors = executorDao.findAll();
            logger.info("Has found next executors : " + foundedExecutors);
            executors = foundedExecutors.stream()
                    .filter(s->s.getStatus() == UserStatus.CONFIRMED)
                    .toList();
            logger.info("Has found next confirmed executors : " + executors);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        return executors;
    }

    @Override
    public Optional<Executor> findDefineExecutor(long executorId) throws ServiceException {
        logger.info("Start findDefineExecutor(long executorId). ExecutorId = " + executorId);
        Optional<Executor> optionalExecutor;
        AbstractDao executorDao = new ExecutorDaoImpl();
        try (EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(executorDao);
            optionalExecutor = executorDao.findEntityById(executorId);
            if (optionalExecutor.isPresent() && optionalExecutor.get().getStatus() != UserStatus.CONFIRMED) {
                optionalExecutor = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        return optionalExecutor;
    }


}
