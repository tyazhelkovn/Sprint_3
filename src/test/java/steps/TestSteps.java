package steps;

import java.io.File;

import builder.CourierRequestBuilder;
import builder.OrderRequestBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TestSteps {
    CourierRequestBuilder courierRequestBuilder = new CourierRequestBuilder();
    OrderRequestBuilder orderRequestBuilder = new OrderRequestBuilder();

    @Step("Creating new courier")
    public Response createCourierStep(File file) {
        return courierRequestBuilder.createCourier(file);
    }

    @Step("Removing test data")
    public void deleteTestDataStep (File file) {
        courierRequestBuilder.deleteTestData(file);
    }

    @Step("Login by new courier")
    public Response loginCourierStep (File file) {
        return courierRequestBuilder.loginCourier(file);
    }

    @Step("Creating new order and saving id to cash")
    public Response createOrderStep(File file) {
        return orderRequestBuilder.createOrder(file);
    }

    @Step("Clearing cash")
    public void clearCash() {
        orderRequestBuilder.clearCash();
    }

    @Step("Canceling order")
    public void cancelOrderStep() {
        orderRequestBuilder.cancelOrder();
    }

    @Step("Getting list of orders")
    public Response getListOfOrdersStep() {
        return orderRequestBuilder.getListOfOrders();
    }

    @Step("Comparing message and status code for bad request")
    public void compareMessageAndStatusCodeForBadRequest(Response response) {
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Step("Comparing body and status code for created request")
    public void compareBodyAndStatusCodeForCreatedCourierRequest(Response response) {
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @Step("Comparing message and status code for conflict request")
    public void compareMessageAndStatusCodeForConflictRequest(Response response) {
        response.then().assertThat().body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Step("Comparing body and status code for success login request")
    public void compareBodyAndStatusCodeForSuccessLoginRequest(Response response) {
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Step("Comparing body and status code for not found request")
    public void compareBodyAndStatusCodeForNotFoundRequest(Response response) {
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Step("Comparing body and status code for bad request")
    public void compareBodyAndStatusCodeForBadRequest(Response response) {
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Step("Comparing body and status code for created request")
    public void compareBodyAndStatusCodeForCreatedOrderRequest(Response response) {
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }

    @Step("Comparing body and status code for success get list request")
    public void compareBodyAndStatusCodeForGetListRequest(Response response) {
        response.then().assertThat().body("orders[0]", notNullValue())
                .and()
                .statusCode(SC_OK);
    }
}
