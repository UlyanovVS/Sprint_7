package api.tests;

import api.clients.ScooterClient;
import api.models.Courier;
import api.models.CourierLogin;
import api.utils.CleanupHelper;
import api.utils.TestDataFactory;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CourierCreateTest {
    private Courier courier;
    private int courierId;

    @Before
    public void createData() {
        courier = TestDataFactory.randomCourier();
    }

    @Test
    public void canCreateCourier() {
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courierId = loginCourier(courier);
    }

    @Test
    public void cannotCreateCourierWithExistingLogin() {
        createCourier(courier);
        createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));
    }

    @Step("Создать курьера")
    private Response createCourier(Courier data) {
        return ScooterClient.givenAuth()
                .body(data)
                .when().post("/api/v1/courier");
    }

    @Step("Логин курьера")
    private int loginCourier(Courier data) {
        CourierLogin loginRequest = new CourierLogin(data.getLogin(), data.getPassword());

        return ScooterClient.givenAuth()
                .body(loginRequest)
                .when().post("/api/v1/courier/login")
                .then().statusCode(200)
                .extract().path("id");
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void canCreateCourierWithoutFirstName() {
        courier.setFirstName(null);
        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @After
    public void cleanup() {
        if (courierId != 0) {
            CleanupHelper.deleteCourier(courierId);
        }
    }
}