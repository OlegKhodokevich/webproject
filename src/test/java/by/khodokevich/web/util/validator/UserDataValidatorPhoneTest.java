package by.khodokevich.web.util.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserDataValidatorPhoneTest {

    @Test(dataProvider = "validate_phone", groups = {"user_validation"})
    public void testIsPhoneValid(String phone, boolean expectedResult) throws ServiceException {
        boolean actualResult =  UserDataValidator.isPhoneValid(phone);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_phone")
    public Object[][] createData() {
        return new Object[][]{
                {"+375293372547", true},
                {"+375333333333", true},
                {"+375456789101", true},
                {"+333333333333", false},
                {"+123456789101", false},
                {"111111111111", false},
                {"1222222222", false},
                {"z", false},
                {"zzzzzzzzzzzzzz", false},
                {"+12345678910s", false},
                {"-375293372547", false},
                {"@375293372547", false},
                {"", false},
                {"Питер12334322222222222222222222222222222222222222222222222222222222asdddddddddddddddddddddddddd", false}};
    }
}