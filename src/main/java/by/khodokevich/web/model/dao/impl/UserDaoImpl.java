package by.khodokevich.web.model.dao.impl;

import static by.khodokevich.web.model.dao.impl.UserColumnName.*;

import by.khodokevich.web.model.builder.UserBuilder;
import by.khodokevich.web.model.dao.AbstractDao;
import by.khodokevich.web.model.dao.UserDao;
import by.khodokevich.web.model.entity.*;
import by.khodokevich.web.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * This class manage entity user in database.
 * It is used for select create or update information connected with user.
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String SQL_SELECT_ALL_USER_ON_PAGE = "SELECT IdUser, FirstName, LastName, EMail, Phone, Region, City, UserStatus, UserRole FROM users JOIN Regions ON Users.IdRegion = Regions.IdRegion ORDER BY IdUser LIMIT ?,?;";
    private static final String SQL_SELECT_DEFINED_USER = "SELECT IdUser, FirstName, LastName, EMail, Phone, Region, City, UserStatus, UserRole FROM users JOIN Regions ON Users.IdRegion = Regions.IdRegion WHERE IdUser = ?;";
    private static final String SQL_SELECT_DEFINED_USER_BY_EMAIL = "SELECT IdUser, FirstName, LastName, EMail, Phone, Region, City, UserStatus, UserRole FROM users JOIN Regions ON Users.IdRegion = Regions.IdRegion WHERE EMail = ?;";
    private static final String SQL_SELECT_DEFINED_USER_ID_BY_PHONE = "SELECT IdUser FROM users WHERE Phone = ?;";
    private static final String SQL_SELECT_USER_PASSWORD = "SELECT EncodedPassword FROM users WHERE IdUser = ?;";
    private static final String SQL_DELETE_DEFINED_USER_BY_ID = "DELETE FROM users WHERE IdUser = ?;";
    private static final String SQL_DELETE_DEFINED_USER_BY_EMAIL = "DELETE FROM users WHERE EMail = ?;";
    private static final String SQL_REGISTER_USER = "INSERT INTO users(FirstName, LastName, EMail, Phone, IdRegion, City, UserStatus, UserRole, EncodedPassword) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER_WITH_CHANGE_EMAIL = "UPDATE users SET FirstName = ?, LastName = ?, EMail = ?, Phone = ?, IdRegion = ?, City = ?, EncodedPassword = ?, UserStatus = ? WHERE IdUser = ?;";
    private static final String SQL_UPDATE_USER_WITH_CHANGE_EMAIL_WITHOUT_PASSWORD = "UPDATE users SET FirstName = ?, LastName = ?, EMail = ?, Phone = ?, IdRegion = ?, City = ?, UserStatus = ? WHERE IdUser = ?;";
    private static final String SQL_UPDATE_USER_WITHOUT_CHANGE_EMAIL = "UPDATE users SET FirstName = ?, LastName = ?, Phone = ?, IdRegion = ?, City = ?, EncodedPassword = ? WHERE IdUser = ?;";
    private static final String SQL_UPDATE_USER_WITHOUT_CHANGE_EMAIL_PASSWORD = "UPDATE users SET FirstName = ?, LastName = ?, Phone = ?, IdRegion = ?, City = ? WHERE IdUser = ?;";
    private static final String SQL_SET_USER_STATUS = "UPDATE users SET UserStatus = ? WHERE IdUser = ?;";
    private static final String SQL_GET_USER_STATUS = "SELECT UserStatus FROM users WHERE IdUser = ?;";
    private static final String SQL_SELECT_NUMBER_ITEMS = "SELECT COUNT(*) FROM users;";

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method searches all user's entity in database.
     * Number of items is limited by pagination.
     *
     * @param pagination information about pagination
     * @return List of users have been found in database
     * @throws DaoException if can't execute query
     */
    public List<User> findAllOnPage(Pagination pagination) throws DaoException {
        logger.info("Start findAllOnPage(Pagination pagination). Pagination " + pagination);
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_ALL_USER_ON_PAGE)) {
            statement.setInt(1, pagination.getLastIndexBeforeFirstItemOnPage());
            statement.setInt(2, pagination.getOnePageNumberItems());
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    long idUser = resultSet.getLong(ID_USER);
                    String firstName = resultSet.getString(FIRSTNAME);
                    String lastName = resultSet.getString(LASTNAME);
                    String eMail = resultSet.getString(E_MAIL);
                    String phone = resultSet.getString(PHONE);
                    Region region = Region.valueOf(resultSet.getString(REGION).toUpperCase());
                    String city = resultSet.getString(CITY);
                    UserStatus status = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                    UserRole role = UserRole.valueOf(resultSet.getString(ROLE_STATUS).toUpperCase());
                    User user = new UserBuilder()
                            .userId(idUser)
                            .firstName(firstName)
                            .lastName(lastName)
                            .eMail(eMail)
                            .phone(phone)
                            .region(region)
                            .city(city)
                            .status(status)
                            .role(role)
                            .buildUser();

                    logger.info("Has found next user = " + user);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next users : " + users);
        return users;
    }

    /**
     * Method searches define user in database by id
     *
     * @param id of user
     * @return optional user.
     * @throws DaoException if can't execute query
     */
    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        logger.info("Start findEntityById(long id). User's ID = " + id);
        User user = null;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_USER)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    long idUser = resultSet.getLong(ID_USER);
                    String firstName = resultSet.getString(FIRSTNAME);
                    String lastName = resultSet.getString(LASTNAME);
                    String eMail = resultSet.getString(E_MAIL);
                    String phone = resultSet.getString(PHONE);
                    Region region = Region.valueOf(resultSet.getString(REGION).toUpperCase());
                    String city = resultSet.getString(CITY);
                    UserStatus status = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                    UserRole role = UserRole.valueOf(resultSet.getString(ROLE_STATUS).toUpperCase());
                    user = new UserBuilder()
                            .userId(idUser)
                            .firstName(firstName)
                            .lastName(lastName)
                            .eMail(eMail)
                            .phone(phone)
                            .region(region)
                            .city(city)
                            .status(status)
                            .role(role)
                            .buildUser();
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next user : " + user);
        return Optional.ofNullable(user);
    }

    /**
     * Method searches user in database by e-mail
     *
     * @param eMail of user
     * @return optional user.
     * @throws DaoException if can't execute query
     */
    @Override
    public Optional<User> findUserByEMail(String eMail) throws DaoException {
        logger.info("Start findUserByEMail(String eMail). Email = " + eMail);
        User user = null;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_USER_BY_EMAIL)) {
            statement.setString(1, eMail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long idUser = resultSet.getLong(ID_USER);
                    String firstName = resultSet.getString(FIRSTNAME);
                    String lastName = resultSet.getString(LASTNAME);
                    String phone = resultSet.getString(PHONE);
                    Region region = Region.valueOf(resultSet.getString(REGION).toUpperCase());
                    String city = resultSet.getString(CITY);
                    UserStatus status = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                    UserRole role = UserRole.valueOf(resultSet.getString(ROLE_STATUS).toUpperCase());
                    user = new UserBuilder()
                            .userId(idUser)
                            .firstName(firstName)
                            .lastName(lastName)
                            .eMail(eMail)
                            .phone(phone)
                            .region(region)
                            .city(city)
                            .status(status)
                            .role(role)
                            .buildUser();
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Find user : " + user);

        return Optional.ofNullable(user);
    }

    /**
     * Method delete information about user by id
     *
     * @param id of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean delete(long id) throws DaoException {
        logger.info("Start delete(long id). User's ID = " + id);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_USER_BY_ID)) {
            statement.setLong(1, id);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " User hasn't been found");
        return result;
    }

    /**
     * Method delete information about user by e-mail
     *
     * @param entity user which will be deleted
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query
     */
    @Override
    public boolean delete(User entity) throws DaoException {
        logger.info("Start delete(User entity)." + entity);
        int numberUpdatedRows;

        try (PreparedStatement statement = super.connection.prepareStatement(SQL_DELETE_DEFINED_USER_BY_EMAIL)) {
            statement.setString(1, entity.getEMail());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean create(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method create user in database.
     *
     * @param entity - user which updated
     * @param password of user
     * @return user's status
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean register(User entity, String password) throws DaoException {
        logger.info("Start create(User entity)." + entity);
        if (entity.getIdUser() != 0) {
            logger.warn("Warning: User's id is define. Id = " + entity.getIdUser());
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_REGISTER_USER)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEMail());
            statement.setString(4, entity.getPhone());
            statement.setInt(5, entity.getRegion().getId());
            statement.setString(6, entity.getCity());
            statement.setString(7, entity.getStatus().name().toLowerCase());
            statement.setString(8, entity.getRole().name().toLowerCase());
            statement.setString(9, password);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Method update information about user by id.
     * User status will be set "declared".
     *
     * @param entity - user which updated
     * @param password of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean updateUserWithChangeEMail(User entity, String password) throws DaoException {
        logger.info("Start update(User entity)." + entity);
        if (entity.getIdUser() == 0) {
            throw new DaoException("User's id = 0. User can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_USER_WITH_CHANGE_EMAIL)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEMail());
            statement.setString(4, entity.getPhone());
            statement.setInt(5, entity.getRegion().getId());
            statement.setString(6, entity.getCity());
            statement.setString(7, password);
            statement.setString(8, UserStatus.DECLARED.name().toLowerCase());
            statement.setLong(9, entity.getIdUser());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method update information about user by id.
     * User status will be set "declared".
     * Password won't be updated.
     *
     * @param entity - user which updated
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean updateUserWithChangeEMailWithoutPassword(User entity) throws DaoException {
        logger.info("Start update(User entity)." + entity);
        if (entity.getIdUser() == 0) {
            throw new DaoException("User's id = 0. User can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_USER_WITH_CHANGE_EMAIL_WITHOUT_PASSWORD)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEMail());
            statement.setString(4, entity.getPhone());
            statement.setInt(5, entity.getRegion().getId());
            statement.setString(6, entity.getCity());
            statement.setString(7, UserStatus.DECLARED.name().toLowerCase());
            statement.setLong(8, entity.getIdUser());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method update information about user by id.
     * E-mail and status won't be updated.
     *
     * @param entity - user which updated
     * @param password of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean updateUserWithoutChangeEMail(User entity, String password) throws DaoException {
        logger.info("Start update(User entity)." + entity);
        if (entity.getIdUser() == 0) {
            throw new DaoException("User's id = 0. User can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_USER_WITHOUT_CHANGE_EMAIL)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setInt(4, entity.getRegion().getId());
            statement.setString(5, entity.getCity());
            statement.setString(6, password);
            statement.setLong(7, entity.getIdUser());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method update information about user by id.
     * E-mail, status and password won't be updated.
     *
     * @param entity - user which updated
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean updateUserWithoutChangeEMailPassword(User entity) throws DaoException {
        logger.info("Start update(User entity)." + entity);
        if (entity.getIdUser() == 0) {
            throw new DaoException("User's id = 0. User can't be updated.");
        }
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_UPDATE_USER_WITHOUT_CHANGE_EMAIL_PASSWORD)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setInt(4, entity.getRegion().getId());
            statement.setString(5, entity.getCity());
            statement.setLong(6, entity.getIdUser());
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method set user status by user id.
     *
     * @param idUser of user
     * @param userStatus of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean setUserStatus(long idUser, UserStatus userStatus) throws DaoException {

        logger.info("Start setUserStatus(long idUser, UserStatus userStatus). Id user = " + idUser + " , status = " + userStatus);
        int numberUpdatedRows;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SET_USER_STATUS)) {
            statement.setString(1, userStatus.name().toLowerCase());
            statement.setLong(2, idUser);
            numberUpdatedRows = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        boolean result = numberUpdatedRows == 1;
        logger.info(() -> result ? "Operation was successful. " : " Operation was failed");
        return result;
    }

    /**
     * Method get user status by user id.
     *
     * @param idUser of user
     * @return user's status
     * @throws DaoException if can't execute query.
     */
    @Override
    public UserStatus getUserStatus(long idUser) throws DaoException {
        logger.info("Start getUserStatus(long idUser). User's ID = " + idUser);
        UserStatus userStatus;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_GET_USER_STATUS)) {
            statement.setLong(1, idUser);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    userStatus = UserStatus.valueOf(resultSet.getString(STATUS).toUpperCase());
                } else {
                    logger.error("No user in database. Id user = " + idUser);
                    throw new DaoException("No user in database. Id user = " + idUser);
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Status =" + userStatus);
        return userStatus;
    }

    /**
     * Method search user by phone.
     *
     * @param phone of user
     * @return true if it is existed, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    @Override
    public boolean checkIsUserExistByPhone(String phone) throws DaoException {
        logger.info("Start checkIsUserExistByPhone(String phone). Phone = " + phone);
        boolean result;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_DEFINED_USER_ID_BY_PHONE)) {
            statement.setString(1, phone);
            try (ResultSet resultSet = statement.executeQuery()) {
                result = resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Result : " + result);
        return result;
    }

    /**
     * Method search encoded password by user id.
     *
     * @param idUser of user
     * @return encoded password
     * @throws DaoException if can't execute query.
     */
    @Override
    public String findUserPasswordById(long idUser) throws DaoException {
        String password;
        logger.info("Start findUserPasswordById(long idUser). User's ID = " + idUser);
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_USER_PASSWORD)) {
            statement.setLong(1, idUser);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    password = resultSet.getString(PASSWORD);
                } else {
                    logger.error("User with that id don't exist.");
                    throw new DaoException("User with that id don't exist.");
                }
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        return password;
    }

    /**
     * Method find number of users in database.
     *
     * @return number of users.
     * @throws DaoException if can't execute query
     */
    @Override
    public int findNumberItems() throws DaoException {
        logger.info("Start findNumberItems().");
        int numberItems = 0;
        try (PreparedStatement statement = super.connection.prepareStatement(SQL_SELECT_NUMBER_ITEMS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                numberItems = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Prepare statement can't be take from connection or unknown field." + e.getMessage());
            throw new DaoException("Prepare statement can't be take from connection or unknown field." + e.getMessage());
        }
        logger.info("Has found next number of items : " + numberItems);
        return numberItems;
    }

}
