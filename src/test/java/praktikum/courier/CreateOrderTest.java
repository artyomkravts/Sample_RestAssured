package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.courier.request.CreateOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private CreateOrder order;
    private String[] color;

    public CreateOrderTest(CreateOrder order, String[] color) {
        this.order = order;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {DataGenerator.getRandomOrder(), new String[] {"GREY"}} ,
                {DataGenerator.getRandomOrder(), new String[] {"BLACK"}} ,
                {DataGenerator.getRandomOrder(), new String[] {"GREY", "BLACK"}} ,
                {DataGenerator.getRandomOrder(), new String[] {""}} ,
        };
    }

    @Test
    @DisplayName("Create order successful")
    @Description("Positive test checks 201 and \"track\" field")
    public void createOrderSuccessful() {
        order.setColor(color);

        Response response = CourierClient.createOrder(order);

        CourierChecks.checkOrderCreatedSuccessful(response);

    }

}
