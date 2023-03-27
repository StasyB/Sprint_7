package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.client.base.ScooterRestClient;

import static io.restassured.RestAssured.given;

public class OrderListSteps extends ScooterRestClient {
    public final static String ORDER_URI = BASE_URI + "orders";

    @Step("Получение списка заказов")
    public ValidatableResponse grtAllOrders() {
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(ORDER_URI)
                .then();
    }
}
