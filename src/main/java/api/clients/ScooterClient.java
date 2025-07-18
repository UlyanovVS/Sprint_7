package api.clients;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ScooterClient {
    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    public static RequestSpecification givenAuth() {
        return RestAssured
                .given()
                .header("Content-Type", "application/json");
    }
}