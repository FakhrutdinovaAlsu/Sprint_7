import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import ru.praktikum.CourierSteps;


public class CreateСourierTest {
private CourierSteps courierSteps = new CourierSteps();
private String login;
private String password;
private String firstName;

    @Before
    public void setUp() {
        login = CourierSteps.returnRandomLogin();
        password = CourierSteps.returnRandomPassword();
        firstName = CourierSteps.returnRandomFirstName();
    }
    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }

    @Test
    public void  shouldReturnOkTrue() {
        courierSteps
                .createCourier(login, password,firstName)
                .statusCode(201).
                body("ok",is(true));
    }

    @Test
    public void  shouldNotCreateTwoIdenticalCouriers() {
        courierSteps
                .createCourier(login, password,firstName);
        courierSteps
                .createCourier(login, password,firstName)
                .statusCode(409)
                .body("message",is("Этот логин уже используется")) ;
    }

    @Test
    public void  shouldNotCreateCourierIfLoginIsBusy() {
        courierSteps
                .createCourier(login, "1234","coutier1");
        courierSteps
                .createCourier(login, "1254","coutier2")
                .statusCode(409)
                .body("message",is("Этот логин уже используется")) ;
    }

    @Test
    public void  invalidCreatingCourierWithoutLogin() {
        courierSteps
                .createCourier("", password,firstName)
                .statusCode(400)
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
    }

    @Test
    public void  invalidCreatingCourierWithoutPassword() {
        courierSteps
                .createCourier(login,"",firstName)
                .statusCode(400)
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
    }
    @Test
    public void  invalidCreatingCourierWithoutFirstName() {
        courierSteps
                .createCourier(login,password,"")
                .statusCode(400).log().all()
                .body("message",is("Недостаточно данных для создания учетной записи")) ;
    }
}