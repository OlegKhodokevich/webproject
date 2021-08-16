package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.controller.command.ParameterAttributeType;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.builder.OrderBuilder;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.impl.ExecutorDaoImpl;
import by.khodokevich.web.model.dao.impl.OrderDaoImpl;
import by.khodokevich.web.model.dao.impl.RevokeDaoImpl;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.service.RevokeService;
import by.khodokevich.web.validator.OrderDataValidator;
import by.khodokevich.web.validator.RevokeDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.controller.command.ParameterAttributeType.RESULT;

public class RevokeServiceImpl implements RevokeService {
    private static final Logger logger = LogManager.getLogger(RevokeServiceImpl.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public List<Revoke> findAllExecutorRevoke(long userExecutorId) throws ServiceException {
        logger.info("Start findAllExecutorRevoke(long userExecutorId). IdUser = " + userExecutorId);
        List<Revoke> revokes;
        try (EntityTransaction transaction = new EntityTransaction()) {
            RevokeDaoImpl revokeDao =new RevokeDaoImpl();
            transaction.beginSingleQuery(revokeDao);
            revokes = revokeDao.findAllExecutorRevoke(userExecutorId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.info("End findUsersOrders(long idUser). Orders = " + revokes);
        return revokes;
    }

    @Override
    public boolean createRevoke(String contractIdString, String description, String markString) throws ServiceException {
        logger.info("Start createRevoke(String contractIdString, String description, String markString). contractIdString = " + contractIdString + " , description = " + description + " , markString = " + markString);
        if (contractIdString == null || description == null || markString == null){
            logger.error("Some enter params are null");
            throw new ServiceException("Some enter params are null");
        }
        boolean result = RevokeDataValidator.isDescriptionValid(description);
        if (result) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            try (EntityTransaction transaction = new EntityTransaction()) {
                RevokeDaoImpl revokeDao = new RevokeDaoImpl();
                ExecutorDaoImpl executorDao = new ExecutorDaoImpl();
                transaction.begin(revokeDao, executorDao);
                long contractId = Long.parseLong(contractIdString);
                int mark = Integer.parseInt(markString);

                Date creationDate = new Date();
                Revoke revoke = new Revoke(contractId, description, mark, creationDate);

                logger.info("Prepare to create next revoke = " + revoke);
                result = revokeDao.create(revoke);
                if (result) {
                }
                transaction.commit();
            }  catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            }  catch (DaoException e) {
                logger.error("Can't create order.", e);
                throw new ServiceException("Can't create order.", e);
            }
        }
        return result;
    }
}
