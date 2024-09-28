package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.LoginCourier;
import java.util.Random;

public class DeleteCourierTest {
    private Integer courierId;

    @Before // Создать курьера - залогинить курьера - достать его id
    public void setUp() {

        CreateCourier courier = DataGenerator.getRandomCourier();
        CourierClient.createCourier(courier);

        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        Response response = CourierClient.logInCourier(loginCourier);

        courierId = CourierClient.getCourierId(response);
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Delete courier successful")
    @Description("Positive test checks 200 and \"ok\": true")
    public void deleteCourierSuccessful() {
        Response response = CourierClient.deleteCourier(courierId);

        CourierChecks.check200AndOkTrue(response);
    }

    @Test
    @DisplayName("Delete unexisting courier failed")
    @Description("Negative test checks 404 Not Found")
    public void deleteUnexistingCourierFailed() {
        Random random = new Random();
        Response response = CourierClient.deleteCourier(random.nextInt(9_000_000_00));

        CourierChecks.check404NotFound(response);
    }

    @Test
    @DisplayName("Delete courier without id failed")
    @Description("Negative test checks 404 Not Found")
    public void deleteCourierWithNoIdFailed() {
        Response response = CourierClient.deleteCourier();

        CourierChecks.check404NotFound(response);
    }

}
