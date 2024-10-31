package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import praktikum.courier.request.CreateOrder;

import java.util.Random;

public class GetOrderByTrackNumTest {
    private Integer trackNum;

    @Test
    @DisplayName("Get order by track number successful")
    @Description("Positive test checks 200 and \"order\"")
    public void getOrderByTrackNumSuccessful() {
        CreateOrder order = DataGenerator.getRandomOrder();
        Response response = CourierClient.createOrder(order);

        trackNum = CourierClient.getTrackNum(response);

        Response response1 = CourierClient.getOrderbyTrackNum(trackNum);

        CourierChecks.check200AndReturnsOrder(response1);
    }

    @Test
    @DisplayName("Get order without track number failed")
    @Description("Negative test checks 400")
    public void getOrderWithoutTrackNumFailed() {
        Response response = CourierClient.getOrderbyTrackNum();

        CourierChecks.check400BadRequest(response);
    }

    @Test
    @DisplayName("Get order with unexisting track number failed")
    @Description("Negative test checks 404")
    public void getOrderByUnexistingTrackNumFailed() {
        Random random = new Random();

        Response response = CourierClient.getOrderbyTrackNum(random.nextInt(900_000_000));

        CourierChecks.check404NotFound(response);
    }
}
