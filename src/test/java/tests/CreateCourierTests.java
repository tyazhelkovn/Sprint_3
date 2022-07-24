package tests;

import java.io.File;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import steps.TestSteps;

public class CreateCourierTests {
    File newCourier = new File("src/test/resources/courier/newCourierWithAllFields.json");
    File loginNewCourier = new File("src/test/resources/login/loginNewCourier.json");
    TestSteps step = new TestSteps();

    @After
    public void tearDown() {
        step.deleteTestDataStep(loginNewCourier);
    }

    @Test
    public void createNewCourierTest() {
        Response response = step.createCourierStep(newCourier);
        step.compareBodyAndStatusCodeForCreatedCourierRequest(response);
    }

    @Test
    public void createSameCouriersTest() {
        step.createCourierStep(newCourier);
        Response response = step.createCourierStep(newCourier);
        step.compareMessageAndStatusCodeForConflictRequest(response);
    }

    @Test
    public void createNewCourierWithOnlyRequiredFieldsTest() {
        Response response = step.createCourierStep(newCourier);
        step.compareBodyAndStatusCodeForCreatedCourierRequest(response);
    }

    @Test
    public void createNewCourierWithOutRequiredLoginFieldTest() {
        File newCourier = new File("src/test/resources/courier/newCourierWithoutRequiredLoginField.json");
        Response response = step.createCourierStep(newCourier);
        step.compareMessageAndStatusCodeForBadRequest(response);
    }

    @Test
    public void createNewCourierWithOutRequiredPasswordFieldTest() {
        File newCourier = new File("src/test/resources/courier/newCourierWithoutRequiredPasswordField.json");
        Response response = step.createCourierStep(newCourier);
        step.compareMessageAndStatusCodeForBadRequest(response);

    }
}
