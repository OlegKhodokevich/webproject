package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.builder.UserBuilder;
import by.khodokevich.web.dao.AbstractDao;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.dao.UserDao;
import by.khodokevich.web.service.CheckingResult;
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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.khodokevich.web.command.ParameterAttributeType.*;
import static by.khodokevich.web.service.CheckingResult.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    public static final String WELCOME = "To activate your account, follow the link ";

    public static final String E_MAIL_CONFIRMATION = "Logging service. E-mail checking.";

    protected UserServiceImpl() {
    }

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

            CheckingResult resultType = SUCCESS;
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                entityTransaction.beginSingleQuery(userDao);

                Optional<User> checkedUser = userDao.findUserByEMail(eMail);
                if (checkedUser.isPresent()) {
                    resultType = DUPLICATE_EMAIL;
                    if (checkedUser.get().getPhone().equals(phone)) {
                        resultType = DUPLICATE_EMAIL_AND_PHONE;
                    }
                }
                if (resultType != DUPLICATE_EMAIL_AND_PHONE) {
                    checkedUser = userDao.findUserByPhone(phone);
                    if (checkedUser.isPresent()) {
                        resultType = (resultType == DUPLICATE_EMAIL) ? DUPLICATE_EMAIL_AND_PHONE : DUPLICATE_PHONE;
                    }
                }

                if (resultType == SUCCESS) {
                    User userForRegistration = new User(firstName, lastName, eMail, phone, RegionBelarus.valueOf(region.toUpperCase()), city, UserStatus.DECLARED, UserRole.CUSTOMER);
                    String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    userDao.register(userForRegistration, encodedPassword);

                    String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
                    String urlPage = userData.get(URL);
                    String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
                    String activatingMessage = WELCOME + url;
                    MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);
                }
                answerMap.put(RESULT, resultType.name());
            } catch (DaoException e) {
                throw new ServiceException(e);
            } catch (Exception e) {
                throw new ServiceException("Error of closing transaction.", e);
            }
        }
        return answerMap;
    }

    @Override
    public Map<String, String> logOn(Map<String, String> userData) throws ServiceException {
        logger.info("Start logIn(Map<String, String> userData). User data = " + userData);
        Map<String, String> answerMap = new HashMap<>();
        CheckingResult resultType;
        String eMail = userData.get(E_MAIL);
        String password = userData.get(PASSWORD);
        if (UserDataValidator.isEMailValid(eMail) && UserDataValidator.isPasswordValid(password)) {
            UserDaoImpl userDao = new UserDaoImpl();
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                entityTransaction.beginSingleQuery(userDao);
                Optional<User> foundUser = userDao.findUserByEMail(eMail);
                if (foundUser.isPresent()) {
                    String encodedPassword = userDao.findUserPasswordById(foundUser.get().getIdUser());
                    if (BCrypt.checkpw(password, encodedPassword)) {
                        UserStatus status = foundUser.get().getStatus();
                        switch (status) {
                            case CONFIRMED:
                                User user = foundUser.get();
                                resultType = SUCCESS;
                                answerMap.put(USER_ID, String.valueOf(user.getIdUser()));
                                answerMap.put(FIRST_NAME, user.getFirstName());
                                answerMap.put(LAST_NAME, user.getLastName());
                                answerMap.put(E_MAIL, user.getEMail());
                                answerMap.put(PHONE, user.getPhone());
                                answerMap.put(REGION, user.getRegion().name());
                                answerMap.put(CITY, user.getCity());
                                answerMap.put(ROLE, user.getRole().name());
                                answerMap.put(STATUS, user.getStatus().name());
                                break;
                            case DECLARED:
                                String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
                                String urlPage = userData.get(URL);
                                String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
                                String activatingMessage = WELCOME + url;
                                MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);
                                resultType = USER_STATUS_NOT_CONFIRM;
                                break;
                            case ARCHIVED:
                                resultType = USER_STATUS_IS_ARCHIVED;
                                break;
                            default:
                                throw new EnumConstantNotPresentException(CheckingResult.class, status.name());
                        }
                    } else {
                        resultType = USER_UNKNOWN;
                    }
                } else {
                    resultType = USER_UNKNOWN;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            } catch (Exception e) {
                throw new ServiceException("Error of closing transaction.", e);
            }
        } else {
            resultType = NOT_VALID;
        }
        answerMap.put(RESULT, resultType.name());
        return answerMap;
    }

    @Override
    public CheckingResult activateUser(String eMail, String token) throws ServiceException {
        CheckingResult answer;
        if (BCrypt.checkpw(eMail, token)) {
            AbstractDao userDao = new UserDaoImpl();

            try (EntityTransaction transaction = new EntityTransaction()) {
                transaction.beginSingleQuery(userDao);
                Optional<User> user = ((UserDao) userDao).findUserByEMail(eMail);

                if (user.isPresent()) {
                    if (user.get().getStatus() == UserStatus.DECLARED) {
                        long idUser = user.get().getIdUser();
                        ((UserDao) userDao).setUserStatus(idUser, UserStatus.CONFIRMED);
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
            } catch (Exception e) {
                throw new ServiceException("Error of closing transaction.", e);
            }
        } else {
            logger.error("Value of token is not confirmed. Email = " + eMail);
            answer = USER_UNKNOWN;
        }
        return answer;
    }

    @Override
    public Optional<User> findDefineUser(long userId) throws ServiceException {

        logger.info("Start findDefineUser(long userId). userId = " + userId);
        UserDaoImpl userDao = new UserDaoImpl();
        Optional<User> user;
        try (EntityTransaction entityTransaction = new EntityTransaction()) {
            entityTransaction.beginSingleQuery(userDao);
            user = userDao.findEntityById(userId);
            if (user.isPresent()) {
                if (user.get().getStatus() == UserStatus.ARCHIVED) {
                    user = Optional.empty();
                    logger.error("Access error. User's status is archived. User id = " + userId);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        return user;
    }

    @Override
    public UserStatus getUserStatus(long userId) throws ServiceException {
        logger.printf(Level.INFO, "Start getUserStatus(long userId). long userId = %d", userId);

        UserDaoImpl userDao = new UserDaoImpl();
        UserStatus userStatus;
        try (EntityTransaction entityTransaction = new EntityTransaction()) {
            entityTransaction.beginSingleQuery(userDao);
            userStatus = userDao.getUserStatus(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException("Error of closing transaction.", e);
        }
        return userStatus;
    }

    @Override
    public Map<String, String> updateUser(Map<String, String> userData) throws ServiceException {
        logger.info("Start updateUser(Map<String, String> userData). User data = " + userData);
        String userIdString = userData.get(USER_ID);
        long userId = Long.valueOf(userIdString);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);
        String password = userData.get(PASSWORD);
        String oldPassword = userData.get(OLD_PASSWORD);

        Map<String, String> answerMap = UserDataValidator.checkUserData(userData);
        String result = answerMap.get(RESULT);
        if (result.equals(SUCCESS.name())) {
            UserDaoImpl userDao = new UserDaoImpl();
            CheckingResult resultType = SUCCESS;
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                entityTransaction.beginSingleQuery(userDao);
                Optional<User> optionalUser = userDao.findEntityById(userId);
                if (UserDataValidator.isPasswordValid(oldPassword)) {
                    String encodedPassword = userDao.findUserPasswordById(userId);
                    if (BCrypt.checkpw(oldPassword, encodedPassword)) {
                        Optional<User> checkedUser;
                        if (optionalUser.isPresent() && !optionalUser.get().getEMail().equalsIgnoreCase(eMail)) {
                            checkedUser = userDao.findUserByEMail(eMail);
                            if (checkedUser.isPresent()) {
                                resultType = DUPLICATE_EMAIL;
                                if (checkedUser.get().getPhone().equals(phone)) {
                                    resultType = DUPLICATE_EMAIL_AND_PHONE;
                                }
                            }
                        }
                        if (optionalUser.isPresent() && !optionalUser.get().getPhone().equalsIgnoreCase(phone)) {
                            if (resultType != DUPLICATE_EMAIL_AND_PHONE) {
                                checkedUser = userDao.findUserByPhone(phone);
                                if (checkedUser.isPresent()) {
                                    resultType = (resultType == DUPLICATE_EMAIL) ? DUPLICATE_EMAIL_AND_PHONE : DUPLICATE_PHONE;
                                }
                            }
                        }
                        boolean resultOperation = true;
                        if (resultType == SUCCESS) {
                            User userForUpdate = new UserBuilder()
                                    .userId(userId)
                                    .firstName(firstName)
                                    .lastName(lastName)
                                    .eMail(eMail)
                                    .phone(phone)
                                    .region(RegionBelarus.valueOf(region.toUpperCase()))
                                    .city(city)
                                    .buildUser();
                            String newEncodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                            if (optionalUser.isPresent() && !optionalUser.get().getEMail().equalsIgnoreCase(eMail)) {
                                resultOperation = userDao.updateUserWithChangEMail(userForUpdate, newEncodedPassword);
                                String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
                                String urlPage = userData.get(URL);
                                String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
                                String activatingMessage = WELCOME + url;
                                MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);
                            } else {
                                resultOperation = userDao.updateUserWithoutChangEMail(userForUpdate, newEncodedPassword);
                                resultType = SUCCESS_WITHOUT_SEND_EMAIL;
                                answerMap.put(ROLE, optionalUser.get().getRole().name());
                                answerMap.put(STATUS, optionalUser.get().getStatus().name());
                            }
                            if (!resultOperation) {
                                resultType = CheckingResult.ERROR;
                            }
                        }
                    } else {
                        resultType = USER_UNKNOWN;
                    }
                } else {
                    resultType = USER_UNKNOWN;
                }
                answerMap.put(RESULT, resultType.name());
            } catch (DaoException e) {
                throw new ServiceException(e);
            } catch (Exception e) {
                throw new ServiceException("Error of closing transaction.", e);
            }
        }
        return answerMap;
    }
}
