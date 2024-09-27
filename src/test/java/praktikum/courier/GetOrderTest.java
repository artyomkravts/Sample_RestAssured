package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.LoginCourier;

public class GetOrderTest {

    private Integer courierId;

    @Before // Создать, залогинить курьера и создать ему заказ
    public void setUp() {
        CreateCourier courier = DataGenerator.getRandomCourier(); // создать курьера
        CourierClient.createCourier(courier);

        LoginCourier courierLogPass = new LoginCourier(courier.getLogin(), courier.getPassword()); // залогинить курьера и достать его id
        courierId = CourierClient.getCourierId(courierLogPass);

//        CreateOrder order = DataGenerator.getRandomOrderWithFirstName(courier.getFirstName()); // создать заказ этому курьеру
//        CourierClient.createOrder(order);                                                      // сейчас неактуально -- заказы не добавляются
    }

    @After
    public void tearDown() {
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Get order returns order list")
    @Description("Positive test checks 200 and \"orders\"")
    public void getOrderReturnsOrderList() {
        Response response = CourierClient.getOrders(courierId);

        CourierChecks.checkGetOrdersReturnsListOfOrders(response);
    }

}
