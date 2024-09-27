package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.CreateOrder;
import praktikum.courier.request.LoginCourier;

import static io.restassured.RestAssured.given;

public class CourierClient { // Методы взаимодействия с курьером

    @Step("Create new courier")
    public static Response createCourier(CreateCourier createCourier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createCourier)
                .when()
                .post(Constants.BASE_URI + Constants.COURIER_CREATE_PATH);
    }

    @Step("Log in courier")
    public static Response logInCourier(LoginCourier loginCourier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(loginCourier)
                .when()
                .post(Constants.BASE_URI + Constants.COURIER_LOGIN_PATH);
    }

    @Step("Delete courier")
    public static void deleteCourier(Integer id) {
        given()
                .delete(Constants.BASE_URI + Constants.COURIER_CREATE_PATH + id);
        System.out.println("deleting courier with id: " + id);
    }

    @Step("Create order")
    static Response createOrder(CreateOrder createOrder) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createOrder)
                .when()
                .post(Constants.BASE_URI + Constants.ORDER_PATH);
    }

    @Step("Get courier id")
    public static Integer getCourierId(LoginCourier courierLogPass) {
        return logInCourier(courierLogPass)
                .then().log().all()
                .extract()
                .path("id");
    }

    @Step("Get orders")
    public static Response getOrders(Integer courierId) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .get(Constants.BASE_URI + Constants.ORDER_PATH + Constants.COURIER_ID_PARAMETER + courierId);
    }
}
