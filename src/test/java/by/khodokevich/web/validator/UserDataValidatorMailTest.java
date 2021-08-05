package by.khodokevich.web.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorMailTest {

    @Test(dataProvider = "validate_e_mail", groups = {"user_validation"})
    public void testIsEMailValid(String eMail, boolean expectedResult) {
        boolean actualResult =  UserDataValidator.isEMailValid(eMail);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_e_mail")
    public Object[][] createData() {
        return new Object[][]{
                {"Ivanov@mail.ru", true},
                {"ivanov@gmail.com", true},
                {"ivanov1@gmail.com", true},
                {"ivanov12_12@gmail.com", true},
                {"peterpeterpeter@yandex.ru", true},
                {"P!@iv.by", false},
                {"Ivanov@ivby", false},
                {"P@iv.", false},
                {"P@.by", false},
                {"P@ivd", false},
                {"Питер@tut.by", false},
                {"PeterTT^@mail.ru", false},
                {"", false},
                {"Питер12334322222222222222222222222222222222222222222222222222222222asdddddddddddddddddddddddddd", false}};
    }
}