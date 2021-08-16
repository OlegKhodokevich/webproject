package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.entity.Pagination;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * This interface manage entity user in database.
 * It is used for select create or update information connected with user.
 */
public interface UserDao {
    /**
     * Method searches all user's entity in database.
     * Number of items is limited by pagination.
     *
     * @param pagination information about pagination
     * @return List of users have been found in database
     * @throws DaoException if can't execute query
     */
    List<User> findAllOnPage(Pagination pagination) throws DaoException;

    /**
     * Method searches user in database by e-mail
     *
     * @param eMail of user
     * @return optional user.
     * @throws DaoException if can't execute query
     */
    Optional<User> findUserByEMail(String eMail) throws DaoException;

    /**
     * Method create user in database.
     *
     * @param user - user which updated
     * @param password of user
     * @return user's status
     * @throws DaoException if can't execute query.
     */
    boolean register(User user, String password) throws DaoException;

    /**
     * Method update information about user by id.
     * User status will be set "declared".
     *
     * @param entity - user which updated
     * @param password of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean updateUserWithChangeEMail(User entity, String password) throws DaoException;

    /**
     * Method update information about user by id.
     * User status will be set "declared".
     * Password won't be updated.
     *
     * @param entity - user which updated
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean updateUserWithChangeEMailWithoutPassword(User entity) throws DaoException;

    /**
     * Method update information about user by id.
     * E-mail and status won't be updated.
     *
     * @param entity - user which updated
     * @param password of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean updateUserWithoutChangeEMail(User entity, String password) throws DaoException;

    /**
     * Method update information about user by id.
     * E-mail, status and password won't be updated.
     *
     * @param entity - user which updated
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean updateUserWithoutChangeEMailPassword(User entity) throws DaoException;

    /**
     * Method set user status by user id.
     *
     * @param idUser of user
     * @param userStatus of user
     * @return true if it is deleted, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean setUserStatus(long idUser, UserStatus userStatus) throws DaoException;

    /**
     * Method get user status by user id.
     *
     * @param idUser of user
     * @return user's status
     * @throws DaoException if can't execute query.
     */
    UserStatus getUserStatus(long idUser) throws DaoException;

    /**
     * Method search user by phone.
     *
     * @param phone of user
     * @return true if it is existed, in other way will return false.
     * @throws DaoException if can't execute query.
     */
    boolean checkIsUserExistByPhone(String phone) throws DaoException;

    /**
     * Method search encoded password by user id.
     *
     * @param idUser of user
     * @return encoded password
     * @throws DaoException if can't execute query.
     */
    String findUserPasswordById(long idUser) throws DaoException;

    /**
     * Method find number of users in database.
     *
     * @return number of users.
     * @throws DaoException if can't execute query
     */
    int findNumberItems() throws DaoException;
}
