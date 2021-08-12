package by.khodokevich.web.model.service.Impl;

import by.khodokevich.web.model.builder.UserBuilder;
import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.dao.UserDao;
import by.khodokevich.web.model.service.CheckingResult;
import by.khodokevich.web.model.dao.impl.UserDaoImpl;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.ServiceException;
import by.khodokevich.web.model.service.UserService;
import by.khodokevich.web.util.MailAuthenticator;
import by.khodokevich.web.validator.UserDataValidator;
import jakarta.mail.MessagingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;
import static by.khodokevich.web.model.service.CheckingResult.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static UserService instance;

    public static final String WELCOME = "To activate your account, follow the link ";
    public static final String E_MAIL_CONFIRMATION = "Logging service. E-mail checking.";
    public static final String TEST = "isTest";
    public static final String TEST_TRUE = "true";

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
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
        String resultCheck = answerMap.get(RESULT);
        if (resultCheck.equals(SUCCESS.name())) {
            CheckingResult resultType;
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                UserDaoImpl userDao = new UserDaoImpl();
                entityTransaction.beginSingleQuery(userDao);
                resultType = checkEMailPhoneForRegistration(eMail, phone, userDao);
                if (resultType == SUCCESS) {
                    User userForRegistration = new UserBuilder().firstName(firstName)
                            .lastName(lastName)
                            .eMail(eMail)
                            .phone(phone)
                            .region(RegionBelarus.valueOf(region.toUpperCase()))
                            .city(city)
                            .status(UserStatus.DECLARED)
                            .role(UserRole.CUSTOMER)
                            .buildUser();
                    String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    userDao.register(userForRegistration, encodedPassword);
                    String urlPage = userData.get(URL);
                    if (userData.get(TEST) == null || !userData.get(TEST).equalsIgnoreCase(TEST_TRUE)) {
                        sendLinkToEMail(eMail, urlPage);
                    }
                }
                answerMap.put(RESULT, resultType.name());
            } catch (DaoException e) {
                throw new ServiceException(e);
            } catch (MessagingException e) {
                logger.error("Attempt of sending e-mail has been failed.");
                resultType = LETTER_NOT_SENT;
                answerMap.put(RESULT, resultType.name());
            } catch (IllegalArgumentException e) {
                logger.error("Region isn't value of Enum.", e);
                throw new ServiceException("Region isn't value of Enum.", e);
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
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                UserDaoImpl userDao = new UserDaoImpl();
                entityTransaction.beginSingleQuery(userDao);
                Optional<User> foundUser = userDao.findUserByEMail(eMail);
                if (foundUser.isPresent()) {
                    String encodedPassword = userDao.findUserPasswordById(foundUser.get().getIdUser());
                    if (BCrypt.checkpw(password, encodedPassword)) {
                        UserStatus status = foundUser.get().getStatus();
                        switch (status) {
                            case CONFIRMED -> {
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
                            }
                            case DECLARED -> {
                                String urlPage = userData.get(URL);
                                sendLinkToEMail(eMail, urlPage);
                                resultType = USER_STATUS_NOT_CONFIRM;
                            }
                            case ARCHIVED -> resultType = USER_STATUS_IS_ARCHIVED;
                            default -> throw new EnumConstantNotPresentException(CheckingResult.class, status.name());
                        }
                    } else {
                        resultType = USER_UNKNOWN;
                    }
                } else {
                    resultType = USER_UNKNOWN;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            } catch (MessagingException e) {
                logger.error("Attempt of sending e-mail has been failed.");
                resultType = LETTER_NOT_SENT;
                answerMap.put(RESULT, resultType.name());
            }
        } else {
            resultType = NOT_VALID;
        }
        answerMap.put(RESULT, resultType.name());
        return answerMap;
    }

    @Override
    public CheckingResult activateUser(String eMail, String token) throws ServiceException {
        CheckingResult resultType;
        if (BCrypt.checkpw(eMail, token)) {
            try (EntityTransaction transaction = new EntityTransaction()) {
                UserDaoImpl userDao = new UserDaoImpl();
                transaction.beginSingleQuery(userDao);
                Optional<User> user = userDao.findUserByEMail(eMail);
                if (user.isPresent()) {
                    if (user.get().getStatus() == UserStatus.DECLARED) {
                        long idUser = user.get().getIdUser();
                        ((UserDao) userDao).setUserStatus(idUser, UserStatus.CONFIRMED);
                        resultType = SUCCESS;
                    } else {
                        resultType = USER_STATUS_NOT_DECLARED;
                    }
                } else {
                    logger.error("Find user not registered. Email = " + eMail);
                    resultType = USER_UNKNOWN;
                }
            } catch (DaoException e) {
                logger.error("Can't activate user. Email = " + eMail, e);
                throw new ServiceException(e);
            }
        } else {
            logger.error("Value of token is not confirmed. Email = " + eMail);
            resultType = USER_UNKNOWN;
        }
        return resultType;
    }

    @Override
    public Optional<User> findDefineUser(long userId) throws ServiceException {

        logger.info("Start findDefineUser(long userId). userId = " + userId);
        Optional<User> optionalUser;
        try (EntityTransaction entityTransaction = new EntityTransaction()) {
            UserDaoImpl userDao = new UserDaoImpl();
            entityTransaction.beginSingleQuery(userDao);
            optionalUser = userDao.findEntityById(userId);
//            if (optionalUser.isPresent()) {
//                if (optionalUser.get().getStatus() == UserStatus.ARCHIVED) {
//                    optionalUser = Optional.empty();
//                    logger.error("Access error. User's status is archived. User id = " + userId);
//                }
//            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    @Override
    public UserStatus getUserStatus(long userId) throws ServiceException {
        logger.printf(Level.INFO, "Start getUserStatus(long userId). long userId = %d", userId);
        UserStatus userStatus;
        try (EntityTransaction entityTransaction = new EntityTransaction()) {
            UserDaoImpl userDao = new UserDaoImpl();
            entityTransaction.beginSingleQuery(userDao);
            userStatus = ((UserDao) userDao).getUserStatus(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userStatus;
    }

    @Override
    public boolean changeUserStatus(long userId, UserStatus status) throws ServiceException {
        logger.info("Start setUserStatus(long userId, UserStatus status). UserId = " + userId + " , status = " + status);
        boolean resultOperation;
        try (EntityTransaction transaction = new EntityTransaction()) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginSingleQuery(userDao);
            resultOperation = ((UserDao) userDao).setUserStatus(userId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultOperation;
    }

    @Override
    public Map<String, String> updateUser(Map<String, String> userData) throws ServiceException {
        logger.info("Start updateUser(Map<String, String> userData). User data = " + userData);
        String userIdString = userData.get(USER_ID);
        long userId = Long.parseLong(userIdString);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);
        String password = userData.get(PASSWORD);
        String confirmedPassword = userData.get(CONFIRMED_PASSWORD);
        String activeRole = userData.get(ACTIVE_USER_ROLE);
        String activeIdString = userData.get(ACTIVE_USER_ID);
        Map<String, String> answerMap = UserDataValidator.checkUserData(userData);
        String result = answerMap.get(RESULT);
        if (result.equals(SUCCESS.name())) {
            CheckingResult resultType;
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                UserDaoImpl userDao = new UserDaoImpl();
                entityTransaction.beginSingleQuery(userDao);
                Optional<User> optionalUser = userDao.findEntityById(userId);
                User user;
                if (UserDataValidator.isPasswordValid(confirmedPassword) && optionalUser.isPresent()) {
                    user = optionalUser.get();
                    resultType = checkEMailPhoneForUpdate(eMail, phone, optionalUser.get(), userDao);
                    String encodedPassword = "";

                    long activeId = Long.parseLong(activeIdString);
                    if (activeRole.equalsIgnoreCase(UserRole.ADMIN.name())) {
                        encodedPassword = userDao.findUserPasswordById(activeId);
                    } else if (activeId == user.getIdUser()) {
                        encodedPassword = userDao.findUserPasswordById(userId);
                    }
                    if (BCrypt.checkpw(confirmedPassword, encodedPassword)) {

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
                            boolean resultOperation;
                            if (!user.getEMail().equalsIgnoreCase(eMail)) {
                                resultOperation = userDao.updateUserWithChangeEMail(userForUpdate, newEncodedPassword);
                                String urlPage = userData.get(URL);
                                sendLinkToEMail(eMail, urlPage);
                                resultType = SUCCESS_WITH_SEND_EMAIL;
                            } else {
                                resultOperation = userDao.updateUserWithoutChangeEMail(userForUpdate, newEncodedPassword);
                                answerMap.put(ROLE, optionalUser.get().getRole().name());
                                answerMap.put(STATUS, optionalUser.get().getStatus().name());
                            }
                            if (!resultOperation) {
                                logger.error("Can't update user with id = " + userId);
                                resultType = CheckingResult.ERROR;
                            }
                        } else {
                            answerMap.put(FIRST_NAME, userData.get(FIRST_NAME));
                            answerMap.put(LAST_NAME, userData.get(LAST_NAME));
                            answerMap.put(E_MAIL, userData.get(E_MAIL));
                            answerMap.put(PHONE, userData.get(PHONE));
                            answerMap.put(REGION, userData.get(REGION));
                            answerMap.put(CITY, userData.get(CITY));
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
            } catch (MessagingException e) {
                logger.error("Attempt of sending e-mail has been failed.");
                resultType = LETTER_NOT_SENT;
                answerMap.put(RESULT, resultType.name());
            } catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            } catch (IllegalArgumentException e) {
                logger.error("Region isn't value of Enum.", e);
                throw new ServiceException("Region isn't value of Enum.", e);
            }
        }
        return answerMap;
    }


    @Override
    public Map<String, String> updateUserWithoutPassword(Map<String, String> userData) throws ServiceException {
        logger.info("Start updateUser(Map<String, String> userData). User data = " + userData);
        String userIdString = userData.get(USER_ID);
        long userId = Long.parseLong(userIdString);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        String eMail = userData.get(E_MAIL);
        String phone = userData.get(PHONE);
        String region = userData.get(REGION);
        String city = userData.get(CITY);
        String confirmedPassword = userData.get(CONFIRMED_PASSWORD);
        String activeRole = userData.get(ACTIVE_USER_ROLE);
        String activeIdString = userData.get(ACTIVE_USER_ID);
        Map<String, String> answerMap = UserDataValidator.checkUserDataWithoutPassword(userData);

        String result = answerMap.get(RESULT);
        if (result.equals(SUCCESS.name())) {
            CheckingResult resultType;
            try (EntityTransaction entityTransaction = new EntityTransaction()) {
                UserDaoImpl userDao = new UserDaoImpl();
                entityTransaction.beginSingleQuery(userDao);
                Optional<User> optionalUser = userDao.findEntityById(userId);
                User user;
                if (UserDataValidator.isPasswordValid(confirmedPassword) && optionalUser.isPresent()) {
                    user = optionalUser.get();
                    resultType = checkEMailPhoneForUpdate(eMail, phone, optionalUser.get(), userDao);
                    String encodedPassword = "";
                    long activeId = Long.parseLong(activeIdString);
                    if (activeRole.equalsIgnoreCase(UserRole.ADMIN.name())) {
                        encodedPassword = userDao.findUserPasswordById(activeId);
                    } else if (activeId == user.getIdUser()) {
                        encodedPassword = userDao.findUserPasswordById(userId);
                    }
                    if (BCrypt.checkpw(confirmedPassword, encodedPassword)) {

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

                            boolean resultOperation;
                            if (!user.getEMail().equalsIgnoreCase(eMail)) {
                                resultOperation = userDao.updateUserWithChangeEMailWithoutPassword(userForUpdate);
                                String urlPage = userData.get(URL);
                                sendLinkToEMail(eMail, urlPage);
                                resultType = SUCCESS_WITH_SEND_EMAIL;
                            } else {
                                resultOperation = userDao.updateUserWithoutChangeEMailPassword(userForUpdate);
                                answerMap.put(ROLE, optionalUser.get().getRole().name());
                                answerMap.put(STATUS, optionalUser.get().getStatus().name());
                            }
                            if (!resultOperation) {
                                logger.error("Can't update user with id = " + userId);
                                resultType = CheckingResult.ERROR;
                            }
                        } else {
                            answerMap.put(FIRST_NAME, userData.get(FIRST_NAME));
                            answerMap.put(LAST_NAME, userData.get(LAST_NAME));
                            answerMap.put(E_MAIL, userData.get(E_MAIL));
                            answerMap.put(PHONE, userData.get(PHONE));
                            answerMap.put(REGION, userData.get(REGION));
                            answerMap.put(CITY, userData.get(CITY));
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
            } catch (MessagingException e) {
                logger.error("Attempt of sending e-mail has been failed.");
                resultType = LETTER_NOT_SENT;
                answerMap.put(RESULT, resultType.name());
            } catch (NumberFormatException e) {
                logger.error("UserId is incorrect. It isn't able to be parsed.", e);
                throw new ServiceException("UserId is incorrect. It isn't able to be parsed.", e);
            } catch (IllegalArgumentException e) {
                logger.error("Region isn't value of Enum.", e);
                throw new ServiceException("Region isn't value of Enum.", e);
            }
        }
        return answerMap;
    }

    @Override
    public List<User> findAllUser() throws ServiceException {
        List<User> users;
        try (EntityTransaction transaction = new EntityTransaction()) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginSingleQuery(userDao);
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    private CheckingResult checkEMailPhoneForUpdate(String eMail, String phone, User user, UserDaoImpl userDao) throws DaoException {
        Optional<User> checkedUser;
        CheckingResult resultType = SUCCESS;
        if (!user.getEMail().equalsIgnoreCase(eMail)) {
            checkedUser = userDao.findUserByEMail(eMail);
            if (checkedUser.isPresent()) {
                resultType = DUPLICATE_EMAIL;
                if (checkedUser.get().getPhone().equals(phone)) {
                    resultType = DUPLICATE_EMAIL_AND_PHONE;
                }
            }
        }
        boolean isUserExistWithSpecifyPhone;
        if (!user.getPhone().equalsIgnoreCase(phone) && resultType != DUPLICATE_EMAIL_AND_PHONE) {
            isUserExistWithSpecifyPhone = userDao.checkIsUserExistByPhone(phone);
            if (isUserExistWithSpecifyPhone) {
                resultType = (resultType == DUPLICATE_EMAIL) ? DUPLICATE_EMAIL_AND_PHONE : DUPLICATE_PHONE;
            }
        }
        return resultType;
    }

    private CheckingResult checkEMailPhoneForRegistration(String eMail, String phone, UserDaoImpl userDao) throws DaoException {
        CheckingResult resultType = SUCCESS;
        Optional<User> checkedUser = userDao.findUserByEMail(eMail);
        if (checkedUser.isPresent()) {
            resultType = DUPLICATE_EMAIL;
            if (checkedUser.get().getPhone().equals(phone)) {
                resultType = DUPLICATE_EMAIL_AND_PHONE;
            }
        }
        boolean isUserExistWithSpecifyPhone;
        if (resultType != DUPLICATE_EMAIL_AND_PHONE) {
            isUserExistWithSpecifyPhone = userDao.checkIsUserExistByPhone(phone);
            if (isUserExistWithSpecifyPhone) {
                resultType = (resultType == DUPLICATE_EMAIL) ? DUPLICATE_EMAIL_AND_PHONE : DUPLICATE_PHONE;
            }
        }
        return resultType;
    }


    private void sendLinkToEMail(String eMail, String urlPage) throws MessagingException {
        String encodedEMail = BCrypt.hashpw(eMail, BCrypt.gensalt());
        String url = urlPage + "?command=activate&token=" + encodedEMail + "&eMail=" + eMail;
        String activatingMessage = WELCOME + url;
        MailAuthenticator.sendEmail(eMail, E_MAIL_CONFIRMATION, activatingMessage);
    }

}
