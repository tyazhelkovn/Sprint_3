package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
    private static RequestSpecification requestSpecification;

    public static RequestSpecification getRequestSpecification() {
        requestSpecification = RestAssured.given()
                .baseUri("http://qa-scooter.praktikum-services.ru");
        return requestSpecification;
    }
}
