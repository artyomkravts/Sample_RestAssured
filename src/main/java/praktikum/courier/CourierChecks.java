package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierChecks { // Методы проверок курьера
    @Step("Check create courier is successful 201")
    public static void checkCreateCourierSuccessful(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CREATED)
                .assertThat()
                .body("ok", is(true));
    }

    @Step("Check creating second courier failed 409")
    static void checkCreateTwoCouriersFailed(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Check create courier field is missing 400")
    static void checkErrorWhenFieldIsMissing(Response response) {
        response.then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Check login is not found 404")
    static void checkLoginNotFound(Response response) {
        response.then()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Check login field is missing 400")
    static void checkLoginFieldMissing(Response response) {
        response.then()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Check login is successful 200")
    static void checkLoginSuccessful(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("id");
    }
    @Step("Check order is created successfully 201")
    static void checkOrderCreatedSuccessful(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CREATED)
                .and()
                .extract()
                .path("track");
    }

    @Step("Check get orders handle returns list of orders 200")
    public static void checkGetOrdersReturnsListOfOrders(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("orders");
    }

    @Step("Check 404 Not Found")
    static void check404NotFound(Response response) {
        response.then().log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Step("Check 200 and \"ok\": \"true\"")
    static void check200AndOkTrue(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("ok", Matchers.equalTo(true));
    }

    @Step("Check 400 Bad Request")
    static void check400BadRequest(Response response) {
        response.then().log().all()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step("Check 200 and returns \"order\"")
    static void check200AndReturnsOrder(Response response) {
        response.then()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("order");
    }
}
