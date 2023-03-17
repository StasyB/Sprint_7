package ru.yandex.practicum;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.practicum.client.CourierSteps;
import ru.yandex.practicum.model.courier.Courier;
import ru.yandex.practicum.model.courier.CourierCredentials;
import ru.yandex.practicum.model.courier.CourierGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierSteps courierSteps;
    private int courierId;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
    }

    @After
    public void deleteCourier() {
        CourierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Курьер успешно авторизовался c валидными логином и паролем")
    public void courierCanBeLoginWithValidData() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courierSteps.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());

    }

    @Test
    @DisplayName("Курьер Не авторизовался c валидными логином и невалидным паролем")
    public void courierCanNotBeLoginWithIncorrectPassword() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courier.setPassword("incorrect");
        courierSteps.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер Не авторизовался c невалидными логином и валидным паролем")
    public void courierCanNotBeLoginWithIncorrectLogin() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courier.setLogin("incorrect");
        courierSteps.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер Не авторизовался без пароля")
    public void courierCanNotBeLoginWithoutPassword() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courier.setPassword(null);
        courierSteps.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер Не авторизовался без логина")
    public void courierCanNotBeLoginWithoutLogin() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courier.setLogin(null);
        courierSteps.loginCourier(CourierCredentials.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
}
