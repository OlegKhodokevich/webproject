package by.khodokevich.web.dao;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.dao.impl.UserDaoImpl;
import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;
import by.khodokevich.web.exception.DaoException;
import by.khodokevich.web.exception.PoolConnectionException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.util.List;

public class UserDaoMain {          //TODO
    public static void main(String[] args) {
        AbstractDao userDaoImpl = new UserDaoImpl();
        Connection connection = null;
        try {
            connection = CustomConnectionPool.getInstance().getConnection();
        } catch (PoolConnectionException e) {
            e.printStackTrace();
        }
        userDaoImpl.setConnection(connection);
        try {
            User user = new User("bortalamey2" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, UserRole.CUSTOMER);
            User user1 = new User("bortalamey1" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, UserRole.CUSTOMER);
            userDaoImpl.create(user);
            userDaoImpl.create(user1);
            List<User> users = userDaoImpl.findAll();
            System.out.println(users);
            userDaoImpl.findEntityById(3);
            System.out.println(userDaoImpl.delete(3));
//            System.out.println(userDao.delete(user1));
//            userDao.create(user);

//            User user = new User("bortalamey" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, UserRole.CUSTOMER);
            users = userDaoImpl.findAll();
            System.out.println(users);
//            User userUpdate = new User(5,"bortalamey55" , BCrypt.hashpw("passwordbortalamey", BCrypt.gensalt(13)), "oleg.khodokevich@mail.ru", "+ 375 29 337 25 47 ", RegionBelarus.HOMYEL_REGION, "Homel", UserStatus.DECLARED, UserRole.CUSTOMER);
//            System.out.println(userDao.update(userUpdate));

//            System.out.println(userDao.create(user));
        } catch (DaoException e) {
            e.printStackTrace();
        }
//        String passwordEncrypt = BCrypt.hashpw("password", BCrypt.gensalt(12));
//        System.out.println(passwordEncrypt);
//        System.out.println(BCrypt.checkpw("password",passwordEncrypt));
//        System.out.println(BCrypt.hashpw("password", BCrypt.gensalt(12)));

    }
}
