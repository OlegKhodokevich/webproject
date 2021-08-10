package by.khodokevich.web.validator;

import by.khodokevich.web.model.service.CheckingResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static by.khodokevich.web.controller.command.ParameterAttributeType.*;

public class OrderDataValidatorTest {

    @Test(groups = {"order_validation"})
    public void testCheckOrderData1() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms.");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        orderData.put(ADDRESS, "Minsk region, Hategino");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.SUCCESS.name());

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData2() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Покраска стен в 2 комнатах.");
        orderData.put(DESCRIPTION, "Покраска стен в 2 комнатах с подготовкой стен и стукатуркой.");
        orderData.put(ADDRESS, "Минский район, Хотежино");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.SUCCESS.name());

        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData3() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms.");
        orderData.put(DESCRIPTION, "Покраска стен в 2 комнатах с подготовкой стен и стукатуркой.");
        orderData.put(ADDRESS, "Minsk region, Hategino");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.SUCCESS.name());


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData4() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. " +
                "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. ");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        orderData.put(ADDRESS, "Minsk region, Hategino");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        expectedAnswer.put(ADDRESS, "Minsk region, Hategino");
        expectedAnswer.put(COMPLETION_DATE, "2021-09-16");
        expectedAnswer.put(SPECIALIZATION, "PAINTING");


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData5() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. " +
                "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. ");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        orderData.put(ADDRESS, "Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. " +
                "Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. ");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        expectedAnswer.put(COMPLETION_DATE, "2021-09-16");
        expectedAnswer.put(SPECIALIZATION, "PAINTING");


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData6() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. " +
                "Painting walls in two rooms. Painting walls in two rooms. Painting walls in two rooms. ");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. " +
                "Painting walls in two rooms with preparing base and plastering. Painting walls in two rooms with preparing base and plastering. ");
        orderData.put(ADDRESS, "Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. " +
                "Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. Minsk region, Hategino. ");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(COMPLETION_DATE, "2021-09-16");
        expectedAnswer.put(SPECIALIZATION, "PAINTING");


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }


    @Test(groups = {"order_validation"})
    public void testCheckOrderData7() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms.");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        orderData.put(ADDRESS, "Minsk region, Hategino");
        orderData.put(COMPLETION_DATE, "2021-09-16");
        orderData.put(SPECIALIZATION, "PAINTING1");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(TITLE, "Painting walls in two rooms.");
        expectedAnswer.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        expectedAnswer.put(ADDRESS, "Minsk region, Hategino");
        expectedAnswer.put(COMPLETION_DATE, "2021-09-16");


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = {"order_validation"})
    public void testCheckOrderData8() {
        Map<String, String> orderData = new HashMap<>();
        orderData.put(TITLE, "Painting walls in two rooms.");
        orderData.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        orderData.put(ADDRESS, "Minsk region, Hategino");
        orderData.put(COMPLETION_DATE, "2020-09-16");
        orderData.put(SPECIALIZATION, "PAINTING1");
        Map<String, String> actualAnswer = OrderDataValidator.checkOrderData(orderData);

        Map<String, String> expectedAnswer = new HashMap<>();
        expectedAnswer.put(RESULT, CheckingResult.NOT_VALID.name());
        expectedAnswer.put(TITLE, "Painting walls in two rooms.");
        expectedAnswer.put(DESCRIPTION, "Painting walls in two rooms with preparing base and plastering.");
        expectedAnswer.put(ADDRESS, "Minsk region, Hategino");


        Assert.assertEquals(actualAnswer, expectedAnswer);
    }
}