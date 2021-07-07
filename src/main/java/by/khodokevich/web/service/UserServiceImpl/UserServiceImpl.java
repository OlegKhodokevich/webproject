package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.command.ParameterAndAttributeType;
import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.dao.UserDao;
import by.khodokevich.web.service.CheckingResultType;
import by.khodokevich.web.dao.impl.UserDaoImpl;
import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.util.MailAuthenticator;
import by.khodokevich.web.validator.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.command.ParameterAndAttributeType.*;
import static by.khodokevich.web.command.ParameterAndAttributeType.CITY;
import static by.khodokevich.web.service.CheckingResultType.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    public static final String WELCOME = "To activate your account, follow the link ";

    public static final String E_MAIL_CONFIRMATION = "Logging service. E-mail checking.";

    @Override
    public Map<String, String> register(Map<String, String> userData) throws ServiceException {
        logger.info("Start register(Map<String, String> userData). User data = " + userData);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);
        String password = userData.get(PASSWORD);
        Map<String, String> answerMap = UserDataValidator.checkUserData(userData);
        String result = answerMap.get(RESULT);
        if (result.equals(SUCCESS.name())) {
            UserDaoImpl userDao = new UserDaoImpl();
            EntityTransaction entityTransaction = new EntityTransaction();
            CheckingResultType resultType = SUCCESS;
            try {
                entityTransaction.beginSingleQuery(userDao);

                Optional<User>  checkedUser = userDao.findUserByEMail(eMail);
                if (checkedUser.isPresent()) {
                    resultType = DUPLICATE_EMAIL;
                    checkedUser = Optional.empty();
                }
                checkedUser = userDao.findUserByPhone(phone);
                if (checkedUser.isPresent()) {
                    resultType = (resultType == DUPLICATE_EMAIL )? DUPLICATE_EMAIL_AND_PHONE : DUPLICATE_PHONE;
                }
                if (resultType == SUCCESS) {
                    User userForRegistretion = new User(firstName, lastName, eMail, phone, RegionBelarus.valueOf(region.toUpperCase()), city, UserStatus.DECLARED, UserRole.CUSTOMER);
                    String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    userDao.register(userForRegistretion, encodedPassword);
                    String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
                    String urlPage = userData.get(URL);
                    String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
                    String activatingMessage = WELCOME + url;
                    MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);

                }
                answerMap.put(RESULT, resultType.name());
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                try {
                    entityTransaction.endSingleQuery();
                } catch (DaoException e) {
                    logger.error("Can't end transaction", e);
                }
            }
        }
        return answerMap;
    }

    @Override
    public Optional<User> logOn(Map<String, String> userData) throws ServiceException {
        logger.info("Start logIn(Map<String, String> userData). User data = " + userData);
        String eMail = userData.get(E_MAIL);
        String password = userData.get(PASSWORD);
        if (!UserDataValidator.isEMailValid(eMail) && UserDataValidator.isPasswordValid(password)) {
            logger.error("User data is not correct.");
            throw new ServiceException("User data is not correct.");
        }
        UserDaoImpl userDao = new UserDaoImpl();
        EntityTransaction entityTransaction = new EntityTransaction();
        Optional<User> user = Optional.empty();
        try {
            entityTransaction.beginSingleQuery(userDao);
            Optional<User> foundUser = userDao.findUserByEMail(eMail);
            if (foundUser.isPresent()) {
                String encodedPassword = userDao.findUserPasswordById(foundUser.get().getIdUser());
                if (BCrypt.checkpw(password, encodedPassword)) {
                    if (foundUser.get().getStatus() == UserStatus.DECLARED) {
                        String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
                        String urlPage = userData.get(URL);
                        String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
                        String activatingMessage = WELCOME + url;
                        MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);
                    }
                    else {
                        user = foundUser;
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            try {
                entityTransaction.endSingleQuery();
            } catch (DaoException e) {
                logger.error("Can't end transaction", e);
            }
        }
        return user;
    }

    @Override
    public CheckingResultType activateUser(String eMail, String token) throws ServiceException {
        CheckingResultType answer;
        if (BCrypt.checkpw(eMail,token)){
            AbstractDao userDao = new UserDaoImpl();
            EntityTransaction transaction = new EntityTransaction();


            try {
                transaction.beginSingleQuery(userDao);
                Optional<User> user = ((UserDao)userDao).findUserByEMail(eMail);

                if (user.isPresent()) {
                    if (user.get().getStatus() == UserStatus.DECLARED) {
                        long idUser = user.get().getIdUser();
                        ((UserDao)userDao).setUserStatus(idUser, UserStatus.CONFIRMED);
                        answer = SUCCESS;
                    } else {
                        answer = USER_STATUS_NOT_DECLARED;
                    }

                } else {
                    logger.error("Find user not registered. Email = " + eMail);
                    answer = USER_UNKNOWN;
                }
            } catch (DaoException e) {
                logger.error("Can't activate user. Email = " + eMail, e);
                throw new ServiceException(e);
            } finally {
                try {
                    transaction.endSingleQuery();
                } catch (DaoException e) {
                    logger.error("Can't end transaction", e);
                }
            }
        } else {
            logger.error("Value of token is not confirmed. Email = " + eMail);
            answer = USER_UNKNOWN;
        }

        return answer;
    }

}
