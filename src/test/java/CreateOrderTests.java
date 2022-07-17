import java.io.File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewOrderWithBlackColorTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithBlackColor.json");
        Response response = createOrder(newOrder);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

        int orderId = response.then().extract().body().path("track");
        cancelOrder(orderId);
    }

    @Test
    public void createNewOrderWithGreyColorTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithGreyColor.json");
        Response response = createOrder(newOrder);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

        int orderId = response.then().extract().body().path("track");
        cancelOrder(orderId);
    }

    @Test
    public void createNewOrderWithBlackAndGreyColorsTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithBlackAndGreyColors.json");
        Response response = createOrder(newOrder);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

        int orderId = response.then().extract().body().path("track");
        cancelOrder(orderId);
    }

    @Test
    public void createNewOrderWithoutColorTest() {
        File newOrder = new File("src/test/resources/order/newOrderWithoutColor.json");
        Response response = createOrder(newOrder);

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

        int orderId = response.then().extract().body().path("track");
        cancelOrder(orderId);
    }

    public Response createOrder(File file) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(file)
                        .when()
                        .post("/api/v1/orders");
    }

    public Response cancelOrder(int id) {
        return
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .put("/api/v1/orders/cancel?track={track}", id);
    }
}
