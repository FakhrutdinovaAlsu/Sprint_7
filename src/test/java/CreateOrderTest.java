import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Step;
import ru.praktikum.OrderSteps;
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
        ValidatableResponse response = OrderSteps.sendPostRequest(color);
        compareTrackNotNullValue(response);
    }

    @Step
        public void compareTrackNotNullValue(ValidatableResponse  response) {
            response.statusCode(201).body("track",notNullValue());
        }
}