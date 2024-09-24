package praktikum.courier;

import org.junit.After;
import org.junit.Test;
import io.restassured.response.Response;

public class CreateCourierTest {

    String testLogin = "ninja1234";
    String testPassword = "1234";
    String testFirstName = "saske";

    @Test
    public void createNewCourierSuccess() {
        CreateCourier courier = new CreateCourier(testLogin, testPassword, testFirstName);

        Response response = CourierClient.createCourier(courier);

        CourierChecks.checkCreateCourierSuccessful(response);
    }

    @After
    public void tearDown() {
        LoginCourier loginCourier = new LoginCourier(testLogin, testPassword);

        LoginSuccess loginSuccess = CourierClient.logInCourier(loginCourier);

        Integer id = loginSuccess.getId();

        CourierClient.deleteCourier(id);
    }


}
