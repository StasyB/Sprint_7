package ru.yandex.practicum.model.order;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderGenerator {

    public static Order gerRandom(String[] color) {
        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(30);
        String metroStation = RandomStringUtils.randomAlphabetic(15);
        String phone = RandomStringUtils.randomAlphabetic(10);
        int rentTime = new Random().nextInt(999);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String comment = RandomStringUtils.randomAlphabetic(50);

        return new Order(firstName, lastName, address,metroStation,phone,rentTime,deliveryDate,comment, color);

    }
}
