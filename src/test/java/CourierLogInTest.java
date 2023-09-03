import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
public class CourierLogInTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

    public static final Courier COURIER = new Courier("ninjaWR12389", "1234", "saske");


    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_SERVICE_URI;
        given().header("Content-type", "application/json")
                .and()
                .body(COURIER.getFullJson())
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201).and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void courierLogInExpectOk() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getNoFirstNameJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);

    }

    @Test
    public void courierNoLoginExpectError() {


        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getNoLoginJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }

    @Test
    //тест не проходит, так как, видимо, баг (обсуждалось в общем чате потока)
    public void courierNoPasswordExpectError() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getNoPasswordJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }

    @Test
    public void courierEmptyPasswordExpectError() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getEmptyPasswordJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);

    }

    @Test
    public void courierUncorrectLoginExpectError() {


        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getUncorrectLoginJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }

    @Test
    public void courierUncorrectPasswordExpectError() {

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(COURIER.getUncorrectPasswordJson())
                        .when()
                        .post("/api/v1/courier/login");

        response.then().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }

    @After
    public void deleteUser() {
        CourierId courierId = given().header("Content-type", "application/json")
                .and()
                .body(COURIER.getNoFirstNameJson())
                .when()
                .post("/api/v1/courier/login")
                .body()
                .as(CourierId.class);

        String clientId = Integer.toString(courierId.getId());
        String deleteBody = "{\"id\": \"" + clientId + "\"}";

        given().header("Content-type", "application/json")
                .and()
                .body(deleteBody)
                .delete("/api/v1/courier/" + clientId)

                .then().statusCode(200);

    }

}
