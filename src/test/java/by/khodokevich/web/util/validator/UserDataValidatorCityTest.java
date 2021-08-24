package by.khodokevich.web.util.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserDataValidatorCityTest {

    @Test(dataProvider = "validate_city", groups = {"user_validation"})
    public void testIsCityValid(String city, boolean expectedResult) throws ServiceException {
        boolean actualResult =  UserDataValidator.isCityValid(city);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_city")
    public Object[][] createData() {
        return new Object[][]{
                {"Minsk", true},
                {"Grodno", true},
                {"Минск", true},
                {"гродно", true},
                {"Гродно1", true},
                {"Гродно!", true},
                {"1222222222", true},
                {"1", false},
                {"R", false},
                {"z", false},
                {"+12345678910s", false},
                {"-375293372547", false},
                {"@375293372547", false},
                {"", false},
                {"Питер12334322222222222222222222222222222222222222222222222222222222asdddddddddddddddddddddddddd", false}};
    }
}