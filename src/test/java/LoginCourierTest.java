import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import ru.praktikum.CourierSteps;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }

    @Test
    public void shouldLogIn() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .loginCourier(login, password)
                .statusCode(200);
    }

    @Test
    public void shouldReturnId() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password,firstName);
        Integer id = courierSteps
                .loginCourier(login, password)
                .extract().path("id");
        assertNotNull("ID курьера не должен быть null", id);
    }

    @Test
    public void shouldNotLogInWithoutLogin() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .loginCourier("", password)
                .statusCode(400)
                .body("message",is("Недостаточно данных для входа"));
    }

    @Test
    public void shouldNotLogInWithoutPassword() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password, firstName);
        courierSteps
                .loginCourier(login, "")
                .statusCode(400)
                .body("message",is("Недостаточно данных для входа"));
    }

    @Test
    public void shouldNotLogInWithIncorrectLogin() {
        returnRandomLogin();
        returnRandomPassword();
        firstName = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .loginCourier(login+"abc", password)
                .statusCode(404)
                .body("message",is("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotLogInWithIncorrectPassword() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .loginCourier(login, password+"abc")
                .statusCode(404)
                .body("message",is("Учетная запись не найдена"));
    }

    @Test
    public void shouldNotLogInIfCourierNotExist() {
        returnRandomLogin();
        returnRandomPassword();
        courierSteps
                .loginCourier(login, password)
                .statusCode(404)
                .body("message",is("Учетная запись не найдена"));
    }

    @Step("Create a random value login")
    public void returnRandomLogin() {
        login = RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Create a random value password")
    public void returnRandomPassword() {
        password = RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Create a random value firstName")
    public void returnRandomFirstName() {
        firstName = RandomStringUtils.randomAlphabetic(10);
    }
}