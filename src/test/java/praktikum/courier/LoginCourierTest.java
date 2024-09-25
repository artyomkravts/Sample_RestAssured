package praktikum.courier;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.LoginCourier;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;


public class LoginCourierTest {

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
        CourierClient.deleteCourier(courierId);
    }

    @Test
    public void loginCourierSuccessful() {
        LoginCourier loginCourier = new LoginCourier(testLogin, testPassword);

        Response response = CourierClient.logInCourier(loginCourier);

        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("id");

        courierId = response.path("id");
    }

    @Test // Ошибка из-за того, что Афтер не может удалить несуществующего клиента -- добавить иф в афтер
    public void loginCourierWithMissingFieldsFailed() {
        LoginCourier[] loginCouriers = {
                new LoginCourier("", testPassword),
                new LoginCourier(testLogin, "")
        };

        for (LoginCourier loginCourier : loginCouriers) {
            Response response = CourierClient.logInCourier(loginCourier);

            response.then()
                    .statusCode(HTTP_BAD_REQUEST)
                    .and()
                    .assertThat()
                    .body("message", equalTo("Недостаточно данных для входаv"));
        }
    }
}
