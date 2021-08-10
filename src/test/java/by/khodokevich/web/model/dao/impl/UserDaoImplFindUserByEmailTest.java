package by.khodokevich.web.model.dao.impl;

import by.khodokevich.web.model.dao.EntityTransaction;
import by.khodokevich.web.model.entity.RegionBelarus;
import by.khodokevich.web.model.entity.User;
import by.khodokevich.web.model.entity.UserRole;
import by.khodokevich.web.model.entity.UserStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

public class UserDaoImplFindUserByEmailTest {


    @Test(groups = "user_dao")
    public void testFindUserByEMail() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        try(by.khodokevich.web.model.dao.EntityTransaction transaction = new EntityTransaction()){
            transaction.beginSingleQuery(userDao);
            String eMail = "Test@test.by";
            Optional<User> actualOptionalUser = userDao.findUserByEMail(eMail);

            Optional<User> expectedOptionalUser = Optional.of(new User(44, "Иван", "Иванов", "Test@test.by", "+375291228877", RegionBelarus.MINSK_REGION, "Минск", UserStatus.CONFIRMED, UserRole.CUSTOMER));
            Assert.assertEquals(actualOptionalUser, expectedOptionalUser);
        }
    }
}