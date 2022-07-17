import java.io.File;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {
    File newCourier = new File("src/test/resources/courier/newCourierWithAllFields.json");
    File loginNewCourier = new File("src/test/resources/login/loginNewCourier.json");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewCourierTest() {
        Response response = createCourier(newCourier);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        deleteTestData(loginNewCourier);
    }

    @Test
    public void createSameCouriersTest() {
        createCourier(newCourier);
        Response response = createCourier(newCourier);

        response.then().assertThat().body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        deleteTestData(loginNewCourier);
    }

    @Test
    public void createNewCourierWithOnlyRequiredFieldsTest() {
        Response response = createCourier(newCourier);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        deleteTestData(loginNewCourier);
    }

    @Test
    public void createNewCourierWithOutRequiredLoginFieldTest() {
        File newCourier = new File("src/test/resources/courier/newCourierWithoutRequiredLoginField.json");
        Response response = createCourier(newCourier);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void createNewCourierWithOutRequiredPasswordFieldTest() {
        File newCourier = new File("src/test/resources/courier/newCourierWithoutRequiredPasswordField.json");
        Response response = createCourier(newCourier);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    public Response createCourier(File file) {
        return
                given()
                        .header("Content-type", "application/json")
                .and()
                .body(file)
                .when()
                .post("/api/v1/courier");
    }

    public void deleteTestData (File file) {
               int courierId = given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(file)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .extract()
                        .body()
                        .path("id");
        given()
                .delete("/api/v1/courier/" + courierId);
    }
}
