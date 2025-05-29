package ru.praktikum;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Получение списка заказов с сервера")
    public static ValidatableResponse getOrderList() {
        return given()
                .when()
                .get("/api/v1/orders")
                .then();
    }

    @Step("Формирование заказа со предпочтительным цветом : {colors}")
    public static ValidatableResponse sendPostRequest(String[] color) {
        OrderDetails orderDetails = new OrderDetails("Guest","Uchiha","onoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","Saske, come back to Konoha",color);
        return given()
                .contentType(ContentType.JSON)
                .body(orderDetails)
                .when()
                .post("/api/v1/orders").then();
    }
}
