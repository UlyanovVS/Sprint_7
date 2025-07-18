package api.utils;

import api.models.Courier;
import api.models.Order;

import java.util.List;
import java.util.UUID;

public class TestDataFactory {
    public static Courier randomCourier() {
        String uuid = UUID.randomUUID().toString().substring(0, 5);
        return new Courier("user_" + uuid, "pass_" + uuid, "Имя" + uuid);
    }

    public static Order randomOrder() {
        return new Order(
                "Иван", "Иванов", "Москва", "Сокольники",
                "88888888888", 2, "01.08.2025", "Комментарий для теста",
                List.of("BLACK")
        );
    }
}
