package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.practicum.client.OrderListSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {

    private OrderListSteps orderListSteps;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @Before
    public void setUp() {
        orderListSteps = new OrderListSteps();
    }

    @Test
    @DisplayName("Получить список заказов")
    public void getAllOrdersArrayWithOrdersNotEmpty() {
        orderListSteps.grtAllOrders()
                .assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
