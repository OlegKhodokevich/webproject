package by.khodokevich.web.dao.impl;

import by.khodokevich.web.connection.CustomConnectionPool;
import by.khodokevich.web.dao.EntityTransaction;
import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class UserDaoImplFindUserByEmailTest {


    @Test(groups = "user_dao")
    public void testFindUserByEMail() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            String eMail = "Test@test.by";
            Optional<User> actualOptionalUser = userDao.findUserByEMail(eMail);

            Optional<User> expectedOptionalUser = Optional.of(new User(44, "Иван", "Иванов", "Test@test.by", "+375291228877", RegionBelarus.MINSK_REGION, "Минск", UserStatus.CONFIRMED, UserRole.CUSTOMER));
            Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
        }
    }
}