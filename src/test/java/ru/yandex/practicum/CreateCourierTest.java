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

public class CreateCourierTest {
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
    @DisplayName("Курьер успешно создан")
    public void courierCanBeCreateWithValidData() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));

        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
    }

    @Test
    @DisplayName("Курьер успешно создан без имени")
    public void courierCanBeCreateWithoutFirstName() {
        Courier courier = CourierGenerator.gerRandom();
        courier.setFirstName(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));

        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
    }

    @Test
    @DisplayName("Курьер не может быть создан без логина")
    public void courierCanNotBeCreateWithoutLogin() {
        Courier courier = CourierGenerator.gerRandom();
        courier.setLogin(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Курьер не может быть создан без пароля")
    public void courierCanNotBeCreateWithoutPassword() {
        Courier courier = CourierGenerator.gerRandom();
        courier.setPassword(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Курьер не может быть создан без логина и пароля")
    public void courierCanNotBeCreateWithoutLoginAndPassword() {
        Courier courier = CourierGenerator.gerRandom();
        courier.setLogin(null);
        courier.setPassword(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void twoIdenticalCouriersCanNotBeCreated() {
        Courier courier = CourierGenerator.gerRandom();

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .assertThat()
                .body("ok", is(true));
        courierId = courierSteps.getIdCourier(CourierCredentials.from(courier));
        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется"));
    }

}
