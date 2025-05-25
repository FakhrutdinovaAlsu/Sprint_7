import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String color;

    public CreateOrderTest(String color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters(name = "Тестовые данные: color")
    public static Object[][] data() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {""},
        };
    }

    @Test
    public void shouldOrderWithDifferentColor() {
        Response response = sendPostRequest();
        compareStatusCode(response);
    }

    @Test
    public void orderShouldHaveTrackNumber() {
        Response response = sendPostRequest();
        compareTrackNotNullValue(response);
    }

    @Step("sendPostRequestOrder")
    public Response sendPostRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstName\": \"Guest\",\n" +
                        "    \"lastName\": \"Uchiha\",\n" +
                        "    \"address\": \"Konoha, 142 apt.\",\n" +
                        "    \"metroStation\": 4,\n" +
                        "    \"phone\": \"+7 800 355 35 35\",\n" +
                        "    \"rentTime\": 5,\n" +
                        "    \"deliveryDate\": \"2020-06-06\",\n" +
                        "    \"comment\": \"Saske, come back to Konoha\",\n" +
                        "    \"color\": [\n" +
                        "        \"" + color + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step
        public void compareTrackNotNullValue(Response response) {
            response.then()
                    .assertThat().body("track",notNullValue());
        }

    @Step
        public void compareStatusCode(Response response) {
            response.then()
                    .statusCode(201);
        }

}