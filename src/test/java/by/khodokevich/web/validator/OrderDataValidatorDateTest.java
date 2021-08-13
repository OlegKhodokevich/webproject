package by.khodokevich.web.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OrderDataValidatorDateTest {

    @Test(dataProvider = "validate_date", groups = {"order_validation"})
    public void testIsCompletionDateValid(String completionDate, boolean expectedResult) throws ServiceException {
        boolean actualResult = OrderDataValidator.isCompletionDateValid(completionDate);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_date")
    public Object[][] createData() {
        return new Object[][]{
                {"2021-12-05", true},
                {"2021-11-05", true},
                {"2022-11-05", true},
                {"2023-1-05", true},
                {"2020-111-05", true},
                {"2020-11-05", false},
                {"2021-11-5", true},
                {"2020-1-05", false},
                {"20-11-05", false},
                {"2020.11.05", false},
                {"2020/11/05", false},
                {"2020-11", false},
                {"2020-11-052", false},
                {"2021-08-04", false},

                {".", false},
                {"", false},
                {" ", false}};
    }
}