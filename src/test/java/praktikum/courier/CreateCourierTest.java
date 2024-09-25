package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateCourierTest {

    String testLogin = "Aboba1234";
    String testPassword = "1234";
    String testFirstName = "saske";

    @After
    public void tearDown() {
        LoginCourier loginCourier = new LoginCourier(testLogin, testPassword);

        LoginSuccess loginSuccess = CourierClient.logInCourier(loginCourier);

        Integer id = loginSuccess.getId();

        CourierClient.deleteCourier(id);

        System.out.println("Courier with id " + id + " is deleted");
    }

    @Test
    @DisplayName("Create new courier successful")
    @Description("201 and \"ok\": true")
    public void createNewCourierSuccess() {
        CreateCourier courier = new CreateCourier(testLogin, testPassword, testFirstName);

        Response response = CourierClient.createCourier(courier);

        CourierChecks.checkCreateCourierSuccessful(response);
    }

    @Test
    @DisplayName("Create 2 couriers failed")
    @Description("409 and \"message\": \"Этот логин уже используется. Попробуйте другой.\"")
    public void createTwoSameCouriersFailed() {
        CreateCourier courier = new CreateCourier(testLogin, testPassword, testFirstName);

        CourierClient.createCourier(courier);

        Response response = CourierClient.createCourier(courier);

        CourierChecks.checkCreateTwoCouriersFailed(response);
    }

    @Test
    public void createNewCourierWithMissingFieldFailed() { // какая-то ошибка
        CreateCourier[] couriers = {
                new CreateCourier("", testPassword, testFirstName),
                new CreateCourier(testLogin, "", testFirstName),
                new CreateCourier(testLogin, testPassword, "")  //Это вообще обязательное поле??
        };

        for (CreateCourier courier : couriers) {
            Response response = CourierClient.createCourier(courier);

            CourierChecks.checkErrorWhenFieldIsMissing(response);
        }
    }


}
