package ru.yandex.practicum;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.client.OrderSteps;
import ru.yandex.practicum.model.order.Order;
import ru.yandex.practicum.model.order.OrderGenerator;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    @Parameterized.Parameter
    public String[] color;

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Object[][] colorData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        OrderSteps orderSteps = new OrderSteps();
        Order order = OrderGenerator.gerRandom(color);

        orderSteps.createOrder(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

}
