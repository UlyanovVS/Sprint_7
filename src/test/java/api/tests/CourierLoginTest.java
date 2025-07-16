package api.tests;

import api.clients.ScooterClient;
import api.models.Courier;
import api.utils.CleanupHelper;
import api.utils.TestDataFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private Courier courier;
    private int courierId;

    @Before
    public void setup() {
        courier = TestDataFactory.randomCourier();
        createCourier(courier).then().statusCode(201);
        courierId = login(courier).then().statusCode(200).extract().path("id");
    }

    @Test
    public void canLogin() {
        login(courier)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void missingFieldFails() {
        login(courier.setPassword(null))
                .then()
                .statusCode(400);
    }

    @Test
    public void wrongCredentialsFail() {
        login(courier.setPassword("wrong"))
                .then()
                .statusCode(404);
    }

    @Step("Создать курьера")
    private Response createCourier(Courier data) {
        return ScooterClient.givenAuth()
                .body(data)
                .when().post("/api/v1/courier");
    }

    @Step("Логин курьера")
    private Response login(Courier data) {
        return ScooterClient.givenAuth()
                .body("{\"login\":\"" + data.getLogin() + "\",\"password\":\"" + data.getPassword() + "\"}")
                .when().post("/api/v1/courier/login");
    }

    @After
    public void cleanup() {
        if (courierId != 0) {
            CleanupHelper.deleteCourier(courierId);
        }
    }
}