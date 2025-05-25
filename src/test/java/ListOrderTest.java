import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;

public class ListOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Getting a list of orders")
    public void getListOfOrder() {
        Response response = sendGetRequestOrder();
        compareStatusCodeAndNotValue(response);
    }

    @Step("sendGetRequestOrder")
    public Response sendGetRequestOrder() {
        Response response = given().get("/api/v1/orders");
        return response;
    }

    @Step("Compare StatusCode and the fact the order is not empty")
    public void compareStatusCodeAndNotValue(Response response ) {
        response.then().statusCode(200).assertThat().body("orders",notNullValue());
    }
}