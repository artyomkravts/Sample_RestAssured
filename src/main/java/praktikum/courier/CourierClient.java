package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public static final String COURIER_PATH = "api/v1/courier/";
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final String COURIER_LOGIN = "api/v1/courier/login/";

    @Step("Create a new courier")
    public static Response createCourier(CreateCourier createCourier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createCourier)
                .when()
                .post(BASE_URI + COURIER_PATH);
    }

    @Step("Log in a courier")
    public static LoginSuccess logInCourier(LoginCourier loginCourier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(loginCourier)
                .when()
                .post(BASE_URI + COURIER_LOGIN)
                .then().log().all()
                .extract().as(LoginSuccess.class);
    }

    @Step("Delete a courier")
    public static void deleteCourier(Integer id) {
        given()
                .delete(BASE_URI + COURIER_PATH + id);
    }
}
