package api.tests;

import api.clients.ScooterClient;
import api.models.Order;
import api.utils.TestDataFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final Order order;

    public OrderCreateTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Object[] data() {
        return new Object[]{
                TestDataFactory.randomOrder().setColor(List.of("BLACK")),
                TestDataFactory.randomOrder().setColor(List.of("GREY")),
                TestDataFactory.randomOrder().setColor(List.of("BLACK", "GREY")),
                TestDataFactory.randomOrder().setColor(List.of())
        };
    }

    @Test
    public void createOrderReturnsTrack() {
        createOrder(order)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Создать заказ")
    private Response createOrder(Order o) {
        return ScooterClient.givenAuth()
                .body(o)
                .when().post("/api/v1/orders");
    }
}