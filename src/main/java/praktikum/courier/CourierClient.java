package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import praktikum.courier.request.CreateCourier;
import praktikum.courier.request.CreateOrder;
import praktikum.courier.request.LoginCourier;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static praktikum.courier.Constants.*;

public class CourierClient { // Методы взаимодействия с курьером

    @Step("Create new courier")
    public static Response createCourier(CreateCourier createCourier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createCourier)
                .when()
                .post(BASE_URI + COURIER_CREATE_PATH);
    }

    @Step("Log in courier")
    public static Response logInCourier(LoginCourier loginCourier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(loginCourier)
                .when()
                .post(BASE_URI + COURIER_LOGIN_PATH);
    }

    @Step("Delete courier")
    public static Response deleteCourier(Integer id) {
        return given().log().all()
                .delete(BASE_URI + COURIER_CREATE_PATH + id);
    }

    @Step("Delete courier without Id")
    public static Response deleteCourier() {
        return given().log().all()
                .delete(BASE_URI + COURIER_CREATE_PATH);
    }

    @Step("Create order")
    static Response createOrder(CreateOrder createOrder) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(createOrder)
                .when()
                .post(BASE_URI + ORDER_PATH);
    }

    @Step("Get orders")
    public static Response getOrders(Integer courierId) {
        return RestAssured.given().log().all()
                .queryParam("courierId", courierId)
                .get(BASE_URI + ORDER_PATH);
    }

    @Step("Get courier id")
    public static Integer getCourierId(Response response) {
        return response.then().log().all()
                .extract()
                .path("id");
    }

    @Step("Get order id")
    public static Integer getOrderId(Response response) {
        return response.then().log().all()
                .extract()
                .path("track");
    }

    @Step("Get order by track number (order id)")
    public static Response getOrderbyTrackNum(Integer orderId) {
         return given().log().all()
                .queryParam(TRACK_PARAMETER, orderId)
                .get(BASE_URI + ORDER_BY_TRACK_PATH);
    }

    @Step("Get order by track number (order id) -- without track number")
    public static Response getOrderbyTrackNum() {
        return given().log().all()
                .queryParam(TRACK_PARAMETER)
                .get(BASE_URI + ORDER_BY_TRACK_PATH);
    }

    @Step("Accept order without courier id")
    public static Response acceptOrderWithoutCourierId(Integer orderId) {
        return given().log().all()
                .queryParam(ID, orderId)
                .put(BASE_URI + ACCEPT_ORDER_PATH);
    }

    @Step("Accept order with unexistingCourierId")
    public static Response acceptOrderWithUnexistingCourierId(Random random, Integer orderId) {
        return given().log().all()
                .queryParam(ID, orderId)
                .queryParam(COURIER_ID_PARAMETER, random.nextInt(900_000_000))
                .put(BASE_URI + ACCEPT_ORDER_PATH);
    }

    @Step("Accept order without order id")
    public  static Response acceptOrderWithoutOrderId(Integer courierId) {
        return given().log().all()
                .queryParam(COURIER_ID_PARAMETER, courierId)
                .put(BASE_URI + ACCEPT_ORDER_PATH);
    }

    @Step("Accept order with unexisting order id")
    public static Response acceptOrderWithUnexistingOrderId(Random random, Integer courierId) {
        return given().log().all()
                .queryParam(ID, random.nextInt(900_000_000))
                .queryParam(COURIER_ID_PARAMETER, courierId)
                .put(BASE_URI + ACCEPT_ORDER_PATH);
    }

    @Step("Accept order")
    public static Response acceptOrder(Integer orderId, Integer courierId) {
        return given().log().all()
                .queryParam(ID, orderId)
                .queryParam(COURIER_ID_PARAMETER, courierId)
                .put(BASE_URI + ACCEPT_ORDER_PATH);
    }
}
