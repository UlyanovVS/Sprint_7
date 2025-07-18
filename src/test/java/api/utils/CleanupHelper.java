package api.utils;

import api.clients.ScooterClient;
import io.qameta.allure.Step;

public class CleanupHelper {
    @Step("Удалить курьера с ID {courierId}")
    public static void deleteCourier(int courierId) {
        ScooterClient.givenAuth()
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .statusCode(200);
    }
}