import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    String baseURI = "https://qa-scooter.praktikum-services.ru";

    String testLogin = "ninja1234";
    String testPassword = "1234";
    String testFirstName = "saske";

    @Test
    public void createNewCourierSuccess() {
        Boolean result = true;

        CreateCourier courier = new CreateCourier(testLogin, testPassword, testFirstName);

        Response response = createCourier(courier);

        checkCreateCourierSuccessful(response);
    }

    @After
    public void tearDown() {
        LoginCourier loginCourier = new LoginCourier(testLogin, testPassword);

        LoginSuccess loginSuccess = logInCourier(loginCourier);

        Integer id = loginSuccess.getId();

        deleteCourier(id);
    }


    @Step("Create a new courier")
    public Response createCourier(CreateCourier createCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post(baseURI + "/api/v1/courier");
    }

    @Step("Log in a courier")
    public LoginSuccess logInCourier(LoginCourier loginCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(baseURI + "/api/v1/courier/login")
                .then().log().all()
                .extract().as(LoginSuccess.class);
    }

    @Step("Delete a courier")
    public void deleteCourier(Integer id) {
        given()
                .delete(baseURI + "/api/v1/courier/" + id);
    }

    @Step("Check that courier is created")
    public void checkCreateCourierSuccessful(Response response) {
        response.then().log().all()
                .statusCode(201)
                .assertThat()
                .body("ok", is(true));
    }

}
