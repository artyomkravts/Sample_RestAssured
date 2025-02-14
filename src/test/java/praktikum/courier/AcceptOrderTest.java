package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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
    private Integer trackNum;
    private Integer orderId;

    @Before
    public void setUp() { // Создать курьера - залогинить курьера - достать его id - создать заказ на имя курьера - достать track заказа - достать id заказа
        CreateCourier courier = DataGenerator.getRandomCourier(); // Создать курьера
        CourierClient.createCourier(courier);

        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword()); // залогинить курьера
        Response response = CourierClient.logInCourier(loginCourier);

        courierId = CourierClient.getCourierId(response); // достать его id

        CreateOrder order = DataGenerator.getRandomOrderWithFirstName(courier.getFirstName()); // создать заказ на имя курьера
        Response response1 = CourierClient.createOrder(order);

        trackNum = CourierClient.getTrackNum(response1); // достать track заказа

        Response response2 = CourierClient.getOrderbyTrackNum(trackNum);

        orderId = CourierClient.getOrderId(response2); // достать id заказа
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Accept order successful")
    @Description("Positive test checks 200 and \"ok\": true")
    public void acceptOrderSuccessful() {
        Response response = CourierClient.acceptOrder(orderId, courierId);
        CourierChecks.check200AndOkTrue(response);
    }

    @Test
    @DisplayName("Accept order without courier id failed")
    @Description("Negative test checks 400")
    public void acceptOrderWithoutCourierIdFailed() {
        Response response = CourierClient.acceptOrderWithoutCourierId(orderId);
        CourierChecks.check400BadRequest(response);
    }

    @Test
    @DisplayName("Accept order with unexisting courier id failed")
    @Description("Negative test checks 404")
    public void acceptOrderWithUnexistingCourierIdFailed() {
        Random random = new Random();
        Response response = CourierClient.acceptOrderWithUnexistingCourierId(random, orderId);
        CourierChecks.check404NotFound(response);
    }

    @Test
    @DisplayName("Accept order without order id failed")
    @Description("Negative test checks 400")
    public void acceptOrderWithoutOrderIdFailed() {
        Response response = CourierClient.acceptOrderWithoutOrderId(courierId);
        CourierChecks.check400BadRequest(response);
    }

    @Test
    @DisplayName("Accept order with unexisting order id failed")
    @Description("Negative test checks 404")
    public void acceptOrderWithUnexistingOrderIdFailed() {
        Random random = new Random();
        Response response = CourierClient.acceptOrderWithUnexistingOrderId(random, courierId);
        CourierChecks.check404NotFound(response);
    }


}
