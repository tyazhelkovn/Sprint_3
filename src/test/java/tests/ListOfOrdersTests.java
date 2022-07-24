package tests;

import java.io.File;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import steps.TestSteps;

public class ListOfOrdersTests {
    TestSteps step = new TestSteps();

    @After
    public void tearDown() {
        step.cancelOrderStep();
        step.clearCash();
    }

    /*
    Небольшие пояснения: здесь проверяется, что в массиве с заказами возвращается хоть какой-то заказ.
    Чтобы быть уверенными, что какой-то заказ действительно изначально должен быть, он создается.
     */
    @Test
    public void getListOfOrdersTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithGreyColor.json");
        Response responseList = step.getListOfOrdersStep();
        step.compareBodyAndStatusCodeForGetListRequest(responseList);
    }
}
