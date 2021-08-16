package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserDataValidatorLastNameTest {

    @Test(dataProvider = "validate_last_name", groups = {"user_validation"})
    public void testIsLastNameValid(String lastName, boolean expectedResult) throws ServiceException {
        boolean actualResult =  UserDataValidator.isLastNameValid(lastName);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_last_name")
    public Object[][] createData() {
        return new Object[][]{
                {"Ivanov", true},
                {"ivanov", true},
                {"peterpeterpeter", true},
                {"P", true},
                {"Питер", true},
                {"PeterTT", true},
                {"", false},
                {"Питер1", false},
                {"Peter#", false},
                {"Peter1", false}};
    }
}