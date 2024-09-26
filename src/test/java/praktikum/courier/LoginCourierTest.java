package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.LoginCourier;


public class LoginCourierTest {

    private boolean shouldRunAfter = true;

    private String testLogin;
    private String testPassword;
    private String testFirstName;

    private Integer courierId;

    @Before
    public void setUp() {
        CreateCourier newCourier = CourierClient.getRandomCourier();

        testLogin = newCourier.getLogin();
        testPassword = newCourier.getPassword();
        testFirstName = newCourier.getFirstName();

        CourierClient.createCourier(newCourier);
    }

    @After
    public void tearDown() {
        if (shouldRunAfter) {
            System.out.println("Running @After method...");
            CourierClient.deleteCourier(courierId);
        } else {
            System.out.println("Skipping @After method for this test...");
        }

    }

    @Test
    @DisplayName("Login courier successful")
    @Description("Positive test checks 200 and \"id\"")
    public void loginCourierSuccessful() {
        LoginCourier loginCourier = new LoginCourier(testLogin, testPassword);

        Response response = CourierClient.logInCourier(loginCourier);

        CourierChecks.checkLoginSuccessful(response);

        courierId = response.path("id");
    }

    @Test // Ошибка из-за того, что Афтер не может удалить несуществующего клиента -- добавить иф в афтер
    @DisplayName("Login courier with missing fields failed")
    @Description("Negative test checks 400 and \"message\": \"Недостаточно данных для входа\"")
    public void loginCourierWithMissingFieldsFailed() {
        shouldRunAfter = false;

        LoginCourier[] loginCouriers = {
                new LoginCourier("", testPassword),
                new LoginCourier(testLogin, "")
        };

        for (LoginCourier loginCourier : loginCouriers) {
            Response response = CourierClient.logInCourier(loginCourier);

            CourierChecks.checkLoginFieldMissing(response);
        }
    }

    @Test
    @DisplayName("Login non-existing courier")
    @Description("Negative test checks 404 and \"message\": \"Учетная запись не найдена\"")
    public void loginNonExistingCourier() {
        Response response = CourierClient.logInCourier(CourierClient.getRandomCourierLogPass());

        CourierChecks.checkLoginNotFound(response);
    }

    @Test
    @DisplayName("Login with incorrect login or password")
    @Description("Negative test checks 404 and \"message\": \"Учетная запись не найдена\"")
    public void loginWithIncorrectLoginOrPassword() {
        LoginCourier loginCourier = new LoginCourier(RandomStringUtils.randomAlphanumeric(15), testPassword);

        Response response = CourierClient.logInCourier(loginCourier);

        CourierChecks.checkLoginNotFound(response);

        LoginCourier loginCourier2 = new LoginCourier(testLogin, RandomStringUtils.randomAlphanumeric(15));

        Response response2 = CourierClient.logInCourier(loginCourier2);

        CourierChecks.checkLoginNotFound(response2);
    }
}
