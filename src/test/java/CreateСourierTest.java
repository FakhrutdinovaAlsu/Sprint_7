import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import ru.praktikum.CourierSteps;


public class CreateСourierTest {
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
    public void  shouldReturnOkTrue() {
        returnRandomLogin();
        returnRandomPassword();
        courierSteps
                .createCourier(login, password,firstName)
                .statusCode(201).
                body("ok",is(true));
    }

    @Test
    public void  shouldNotCreateTwoIdenticalCouriers() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .createCourier(login, password,firstName)
                .statusCode(409)
                .body("message",is("Этот логин уже используется")) ;
    }

    @Test
    public void  shouldNotCreateCourierIfLoginIsBusy() {
        returnRandomLogin();
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier(login, "1234","coutier1");
        courierSteps
                .createCourier(login, "1254","coutier2")
                .statusCode(409)
                .body("message",is("Этот логин уже используется")) ;
    }

    @Test
    public void  invalidCreatingCourierWithoutLogin() {
        returnRandomPassword();
        returnRandomFirstName();
        courierSteps
                .createCourier("", password,firstName)
                .statusCode(400)
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
    }

    @Test
    public void  invalidCreatingCourierWithoutPassword() {
        returnRandomFirstName();
        returnRandomLogin();
        courierSteps
                .createCourier(login,"",firstName)
                .statusCode(400)
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
    }
    @Test
    public void  invalidCreatingCourierWithoutFirstName() {
        returnRandomFirstName();
        returnRandomLogin();
        courierSteps
                .createCourier(login,password,"")
                .statusCode(400).log().all()
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
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