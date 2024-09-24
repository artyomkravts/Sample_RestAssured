package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.is;

public class CourierChecks {
    @Step("Check that courier is created")
    public static void checkCreateCourierSuccessful(Response response) {
        response.then().log().all()
                .statusCode(HTTP_CREATED)
                .assertThat()
                .body("ok", is(true));
    }
}
