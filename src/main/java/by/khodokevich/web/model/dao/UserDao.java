package by.khodokevich.web.model.dao;

import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean setUserStatus(long idUser, UserStatus userStatus) throws DaoException;

    UserStatus getUserStatus(long idUser) throws DaoException;

    boolean register(User user, String password) throws DaoException;

    Optional<User> findUserByEMail(String eMail) throws DaoException;

    boolean checkIsUserExistByEMail(String eMail) throws DaoException;


    boolean checkIsUserExistByPhone(String phone) throws DaoException;


    String findUserPasswordById(long idUser) throws DaoException;

    boolean updateUserWithChangeEMail(User entity, String password) throws DaoException;

    boolean updateUserWithChangeEMailWithoutPassword(User entity) throws DaoException;

    boolean updateUserWithoutChangeEMail(User entity, String password) throws DaoException;

    boolean updateUserWithoutChangeEMailPassword(User entity) throws DaoException;
}
