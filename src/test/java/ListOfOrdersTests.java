import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    /*
    Небольшие пояснения: здесь проверяется, что в массиве с заказами возвращается хоть какой-то заказ.
    Чтобы быть уверенными, что какой-то заказ действительно изначально должен быть, он создается.
     */
    @Test
    public void getListOfOrdersTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithGreyColor.json");
        CreateOrderTests createOrderTests = new CreateOrderTests();
        Response responseOrder = createOrderTests.createOrder(newOrder);
        Response responseList = getListOfOrders();

        responseList.then().assertThat().body("orders[0]", notNullValue())
                .and()
                .statusCode(200);

        int orderId = responseOrder.then().extract().path("track");
        createOrderTests.cancelOrder(orderId);
    }

    public Response getListOfOrders() {
        return
                given()
                        .get("/api/v1/orders");
    }
}
