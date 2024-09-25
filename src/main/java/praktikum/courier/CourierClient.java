package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient { // Методы взаимодействия с курьером

    @Step("Create a new courier")
    public static Response createCourier(CreateCourier createCourier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createCourier)
                .when()
                .post(Constants.BASE_URI + Constants.COURIER_CREATE_PATH);
    }

    @Step("Log in a courier")
    public static LoginSuccess logInCourier(LoginCourier loginCourier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(loginCourier)
                .when()
                .post(Constants.BASE_URI + Constants.COURIER_LOGIN_PATH)
                .then().log().all()
                .extract().as(LoginSuccess.class);
    }

    @Step("Delete a courier")
    public static void deleteCourier(Integer id) {
        given()
                .delete(Constants.BASE_URI + Constants.COURIER_CREATE_PATH + id);
        System.out.println("deleting courier with id: " + id);
    }
}
