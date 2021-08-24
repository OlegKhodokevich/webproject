package by.khodokevich.web.util.validator;

import by.khodokevich.web.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrderDataValidatorDescriptionTest {

    @Test(dataProvider = "validate_description", groups = {"order_validation"})
    public void testIsDescriptionValid(String description, boolean expectedResult) throws ServiceException {
        boolean actualResult = OrderDataValidator.isDescriptionValid(description);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_description")
    public Object[][] createData() {
        return new Object[][]{
                {"Укладка плитки", true},
                {"Laying tiles", true},
                {"Укладка 10 м2 паркета", true},
                {"Covering 10 m2 parquet", true},
                {"Covering 10 m2 parquet.!?", true},
                {"Укладка 10 м2 паркета. Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!", true},
                {"BR", true},
                {"Covering 10 m2 parquet.!?@", true},
                {"Укладка 10 м2 паркета(многослойный)", true},
                {"Укладка 10 м2 паркета^", true},
                {"Укладка 10 м2 паркета.  Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! " +
                        "Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!" +
                        " Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!" +
                        " Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!" +
                        " Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!" +
                        " Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot! Много, очень много. A lot!", false},
                {".", false},
                {"", false},
                {" ", false}};
    }
}