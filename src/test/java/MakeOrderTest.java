import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


    public class MakeOrderTest {

        public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

        @Before
        public void setUp() {
            RestAssured.baseURI = SCOOTER_SERVICE_URI;
        }


        @Test
        public void createOrderWithTwoColorsExpectOk() {
            String[] colors = {"BLACK", "GREY",};
            Order order = new Order("Naruto",
                    "Uchiha",
                    "Konoha, 142 apt.",
                    "Сокол",
                    "+7 800 355 35 35",
                    5,
                    "2020-06-06",
                    "Saske, come back to Konoha",
                    colors);
            given().header("Content-type", "application/json")
                    .and()
                    .body(order)
                    .when()
                    .post("/api/v1/orders")
                    .then().statusCode(201).and()
                    .assertThat().body("track", notNullValue());

        }

        @Test
        public void createOrderWithNoColorsExpectOk() {

            Order order = new Order("Naruto",
                    "Uchiha",
                    "Konoha, 142 apt.",
                    "Сокол",
                    "+7 800 355 35 35",
                    5,
                    "2020-06-06",
                    "Saske, come back to Konoha");
            given().header("Content-type", "application/json")
                    .and()
                    .body(order)
                    .when()
                    .post("/api/v1/orders")
                    .then().statusCode(201).
                    and()
                    .assertThat().body("track", notNullValue());

        }

}
