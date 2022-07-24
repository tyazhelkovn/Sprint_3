package builder;

import java.io.File;

import base.BaseClass;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierRequestBuilder extends BaseClass {
    private final static String COURIER_PATH = "/api/v1/courier/";

    public Response createCourier(File file) {
        return
                given()
                        .spec(getRequestSpecification())
                        .header("Content-type", "application/json")
                        .and()
                        .body(file)
                        .when()
                        .post(COURIER_PATH);
    }

    public void deleteTestData (File file) {
        int courierId;
        Response response = given()
                .spec(getRequestSpecification())
                .header("Content-type", "application/json")
                .and()
                .body(file)
                .when()
                .post(COURIER_PATH + "login");
        if (!(response.then().extract().body().path("id") == null)) {
            courierId = response.then().extract().body().path("id");
            given()
                    .spec(getRequestSpecification())
                    .delete(COURIER_PATH + courierId);
        }
    }

    public Response loginCourier (File file) {
        return given()
                .spec(getRequestSpecification())
                .header("Content-type", "application/json")
                .and()
                .body(file)
                .when()
                .post(COURIER_PATH + "login");
    }
}
