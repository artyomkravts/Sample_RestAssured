package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierChecks { // Методы проверок курьера
    @Step("Check that courier is created 201")
    public static void checkCreateCourierSuccessful(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CREATED)
                .assertThat()
                .body("ok", is(true));
    }

    @Step("Check that creating second courier failed 409")
    static void checkCreateTwoCouriersFailed(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Check 400 error when field is missing")
    static void checkErrorWhenFieldIsMissing(Response response) {
        response.then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
