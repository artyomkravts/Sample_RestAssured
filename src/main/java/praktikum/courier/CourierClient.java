package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import praktikum.courier.request.CreateCourier;
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




    public static CreateCourier getRandomCourier() {
        String testLogin = "TestGuy" + RandomStringUtils.randomAlphanumeric(15);
        String testPassword = RandomStringUtils.randomNumeric(5);
        String testFirstName = RandomStringUtils.randomAlphabetic(10);

        return new CreateCourier(testLogin, testPassword, testFirstName);
    }

    public static LoginCourier getRandomCourierLogPass() {
        String testLogin = "TestGuy" + RandomStringUtils.randomAlphanumeric(15);
        String testPassword = RandomStringUtils.randomNumeric(5);

        return new LoginCourier(testLogin, testPassword);
    }
}
