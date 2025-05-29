import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import ru.praktikum.OrderSteps;

public class ListOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Getting a list of orders")
    public void getListOfOrder() {
        ValidatableResponse response = OrderSteps.getOrderList();
        compareStatusCodeAndNotValue(response);
    }

    @Step("Compare StatusCode and the fact the order is not empty")
    public void compareStatusCodeAndNotValue(ValidatableResponse  response ) {
        response.statusCode(200).body("orders",notNullValue());
    }
}