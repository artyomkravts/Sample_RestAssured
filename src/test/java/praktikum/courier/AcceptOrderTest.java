package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.CreateOrder;
import praktikum.courier.request.LoginCourier;

import java.util.Random;

public class AcceptOrderTest {
    private Integer courierId;
    private Integer orderId;

    @Before
    public void setUp() { // Создать курьера - залогинить курьера - достать его id - создать заказ - достать id заказа
        CreateCourier courier = DataGenerator.getRandomCourier();
        CourierClient.createCourier(courier);

        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        Response response = CourierClient.logInCourier(loginCourier);

        courierId = CourierClient.getCourierId(response);

        CreateOrder order = DataGenerator.getRandomOrderWithFirstName(courier.getFirstName());
        Response response1 = CourierClient.createOrder(order);

        orderId = CourierClient.getOrderId(response1);
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Accept order successful")
    @Description("Positive test checks 200 and \"ok\": true")
    public void acceptOrderSuccessful() {
        Response response = RestAssured.given().log().all()
                .queryParam(Constants.ID, orderId)
                .queryParam(Constants.COURIER_ID_PARAMETER, courierId)
                .put(Constants.BASE_URI + Constants.ACCEPT_ORDER_PATH);
        CourierChecks.check200AndOkTrue(response);
    }

    @Test
    public void acceptOrderWithoutCourierIdFailed() {
        Response response = RestAssured.given().log().all()
                .queryParam(Constants.ID, orderId)
                .put(Constants.BASE_URI + Constants.ACCEPT_ORDER_PATH);
        CourierChecks.check400BadRequest(response);
    }

    @Test
    public void acceptOrderWithUnexistingCourierIdFailed() {
        Random random = new Random();
        Response response = RestAssured.given().log().all()
                .queryParam(Constants.ID, orderId)
                .queryParam(Constants.COURIER_ID_PARAMETER, random.nextInt(900_000_000))
                .put(Constants.BASE_URI + Constants.ACCEPT_ORDER_PATH);
        CourierChecks.check404NotFound(response);
    }

    @Test
    public void acceptOrderWithoutOrderIdFailed() {
        Response response = RestAssured.given().log().all()
                .queryParam(Constants.COURIER_ID_PARAMETER, courierId)
                .put(Constants.BASE_URI + Constants.ACCEPT_ORDER_PATH);
        CourierChecks.check400BadRequest(response);
    }

    @Test
    public void acceptOrderWithUnexistingOrderIdFailed() {
        Random random = new Random();
        Response response = RestAssured.given().log().all()
                .queryParam(Constants.ID, random.nextInt(900_000_000))
                .queryParam(Constants.COURIER_ID_PARAMETER, courierId)
                .put(Constants.BASE_URI + Constants.ACCEPT_ORDER_PATH);
        CourierChecks.check404NotFound(response);
    }


}
