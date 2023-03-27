package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.client.base.ScooterRestClient;
import ru.yandex.practicum.model.order.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps extends ScooterRestClient {
    public final static String ORDER_URI = BASE_URI + "orders/";
    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseReqSpec())
                .body(order)
                .when()
                .post(ORDER_URI)
                .then();
    }

}
