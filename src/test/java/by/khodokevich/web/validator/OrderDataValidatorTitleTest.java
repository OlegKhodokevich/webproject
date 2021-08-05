package by.khodokevich.web.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OrderDataValidatorTitleTest {


    @Test(dataProvider = "validate_title", groups = {"order_validation"})
    public void testIsTitleValid(String title, boolean expectedResult) {
        boolean actualResult = OrderDataValidator.isTitleValid(title);

        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validate_title")
    public Object[][] createData() {
        return new Object[][]{
                {"Укладка плитки", true},
                {"Laying tiles", true},
                {"Укладка 10 м2 паркета", true},
                {"Covering 10 m2 parquet", true},
                {"Covering 10 m2 parquet.!?", true},
                {"BR", true},
                {"Covering 10 m2 parquet.!?@", true},
                {"Укладка 10 м2 паркета(многослойный)", true},
                {"Укладка 10 м2 паркета^", true},
                {"Укладка 10 м2 паркета^уфввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввввв", false},
                {".", false},
                {"", false},
                {" ", false}};
    }
}