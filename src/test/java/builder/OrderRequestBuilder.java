package builder;

import java.io.File;

import base.BaseClass;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderRequestBuilder {
    BaseClass baseClass = new BaseClass();
    Integer cashId = null;
    private final static String ORDER_PATH = "/api/v1/orders";

    public Response createOrder(File file) {
        Response response =
                given()
                        .spec(baseClass.getRequestSpecification())
                        .header("Content-type", "application/json")
                        .and()
                        .body(file)
                        .when()
                        .post(ORDER_PATH);
        saveOrderIdToCash(response);
        return response;
    }

    public void cancelOrder() {
        if (!(cashId == null)) {
            given()
                    .spec(baseClass.getRequestSpecification())
                    .header("Content-type", "application/json")
                    .when()
                    .put(ORDER_PATH + "/cancel?track={track}", cashId);
        }
    }

    public Response getListOfOrders() {
        return
                given()
                        .spec(baseClass.getRequestSpecification())
                        .get(ORDER_PATH);
    }

    public void saveOrderIdToCash(Response response) {
        cashId = response.then().extract().body().path("track");
    }

    public void clearCash() {
        cashId = null;
    }
}
