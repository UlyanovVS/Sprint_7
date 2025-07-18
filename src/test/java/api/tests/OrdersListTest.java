package api.tests;

import api.clients.ScooterClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {
    @Test
    public void ordersListIsReturned() {
        getOrdersList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

    @Step("Получить список заказов")
    private Response getOrdersList() {
        return ScooterClient.givenAuth()
                .when().get("/api/v1/orders");
    }
}