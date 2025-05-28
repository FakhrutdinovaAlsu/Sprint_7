import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Step;
import ru.praktikum.OrderDetails;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String[] color;

    public CreateOrderTest(String colorData) {
        if (colorData.isEmpty()) {
            this.color = new String[0];
        } else {
            this.color = colorData.split(", ");
        }
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
    public void orderShouldHaveTrackNumber() {
        Response response = sendPostRequest(color);
        compareTrackNotNullValue(response);
    }

    @Step("sendPostRequestOrder")
    public Response sendPostRequest(String[] color) {
        OrderDetails orderDetails = new OrderDetails("Guest","Uchiha","onoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","Saske, come back to Konoha",color);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(orderDetails)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step
        public void compareTrackNotNullValue(Response response) {
            response.then()
                    .assertThat().statusCode(201).body("track",notNullValue());
        }

}