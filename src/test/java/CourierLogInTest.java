import courier.Courier;
import courier.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.CoreMatchers.*;
public class CourierLogInTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
    private static final String LOGIN = "ninja"+RandomStringUtils.randomAlphabetic(5);
    private static final String PASSWORD = "1234";
    private static final String FIRSTNAME = "saske";
    public static final Courier COURIER = new Courier(LOGIN, PASSWORD, FIRSTNAME);
    private static String id;


    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = SCOOTER_SERVICE_URI;
        CourierApi.createCourier(COURIER)
                .then().statusCode(SC_CREATED).and()
                .assertThat().body("ok", equalTo(true));
        id = CourierApi.logInCourier(COURIER).then().extract().path("id").toString();

    }

    @Test
    @DisplayName("Create courier with correct login and password, result ok")
    public void courierLogInExpectOk() {

        Response response = CourierApi.logInCourier(new Courier(LOGIN, PASSWORD));

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);

    }

    @Test
    @DisplayName("Create courier without login, result error")
    public void courierNoLoginExpectError() {

        Response response = CourierApi.logInCourier(new Courier(null, PASSWORD));

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Create courier without password, result error")
    @Description("тест не проходит, так как, видимо, баг (обсуждалось в общем чате потока)")
    public void courierNoPasswordExpectError() {

        Response response = CourierApi.logInCourier(new Courier(LOGIN, null));

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Create courier with empty password, result error")
    public void courierEmptyPasswordExpectError() {

        Response response = CourierApi.logInCourier(new Courier(LOGIN, ""));

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Create courier with incorrect login, result error")
    public void courierIncorrectLoginExpectError() {

        Response response = CourierApi.logInCourier(new Courier(LOGIN+"11", PASSWORD));

        response.then().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Create courier with incorrect password, result error")
    public void courierIncorrectPasswordExpectError() {

        Response response = CourierApi.logInCourier(new Courier(LOGIN, PASSWORD+"11"));

        response.then().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);

    }

    @AfterClass
    public static void deleteUser() {

        CourierApi.deleteCourier(id);

    }

}
