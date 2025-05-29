package ru.praktikum;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    public ValidatableResponse createCourier(String login, String password, String firstName) {
        LogInDetails logInDetails = new LogInDetails(login, password,firstName);
        return (ValidatableResponse) given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(logInDetails)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginCourier(String login, String password) {
        LogInDetails logInDetails = new LogInDetails(login, password);
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .body(logInDetails)
                .when()
                .post("/api/v1/courier/login")
                .then();

    }

    public ValidatableResponse deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
