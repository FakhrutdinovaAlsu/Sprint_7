package ru.praktikum;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создание курьера с логином '{login}', паролем '{password}' и именем '{firstName}'")
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

    @Step("Авторизация курьера с логином '{login}' и паролем '{password}'")
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

    @Step("Удаление курьера с ID {id}")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }

    @Step("Create a random value login")
    public static String returnRandomLogin() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Create a random value password")
    public static String returnRandomPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Create a random value firstName")
    public static String returnRandomFirstName() {
        return  RandomStringUtils.randomAlphabetic(10);
    }
}