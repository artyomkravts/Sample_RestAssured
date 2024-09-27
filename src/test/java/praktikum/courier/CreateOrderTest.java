package praktikum.courier;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.CreateOrder;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

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
    public void createOrderSuccessful() {
        order.setColor(color);

        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(Constants.BASE_URI + Constants.CREATE_ORDER_PATH);

        response.then().log().all()
                .statusCode(HTTP_CREATED)
                .and()
                .extract()
                .path("track");

    }
}
