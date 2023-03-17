package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.client.base.ScooterRestClient;
import ru.yandex.practicum.model.courier.Courier;
import ru.yandex.practicum.model.courier.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierSteps extends ScooterRestClient {
    public final static String COURIER_URI = BASE_URI + "courier/";

    @Step("Создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseReqSpec())
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then();
    }

    @Step("Залогинить курьера")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseReqSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_URI + "login/")
                .then();
    }

    @Step("Удалить курьера")
    public static ValidatableResponse deleteCourier(int courierId) {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .delete(COURIER_URI + courierId)
                .then();
    }

    public int getIdCourier(CourierCredentials courierCredentials) {
        return loginCourier(courierCredentials).extract().path("id");
    }
}