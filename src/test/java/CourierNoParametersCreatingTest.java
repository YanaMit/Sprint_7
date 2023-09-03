import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierNoParametersCreatingTest {

        public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
        public static final Courier COURIER = new Courier("ninjaWR12389", "1234", "saske");

        @Before
        public void setUp() {
            RestAssured.baseURI = SCOOTER_SERVICE_URI;
        }


        @Test
        public void createCourierWithoutLoginError() {
            given().header("Content-type", "application/json")
                    .and()
                    .body(COURIER.getNoLoginJson())
                    .when()
                    .post("/api/v1/courier")
                    .then().statusCode(400);


            given().header("Content-type", "application/json")
                    .and()
                    .body(COURIER.getNoLoginJson())
                    .when()
                    .post("/api/v1/courier")
                    .then().assertThat().
                    body("message", equalTo("Недостаточно данных для создания учетной записи"));

        }


    @Test
    public void createCourierWithoutPasswordError() {
        given().header("Content-type", "application/json")
                .and()
                .body(COURIER.getNoPasswordJson())
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
       }


    @Test
    public void createCourierWithoutPasswordWithoutLoginError() {
        given().header("Content-type", "application/json")
                .and()
                .body(COURIER.getNoPasswordNoLoginJson())
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}

