import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;



public class ListOfOrdersTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_SERVICE_URI;
    }


    @Test
    public void listOfOrdersExpectNotEmpty() {

        given().header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders")
                .then().statusCode(200).and()
                .assertThat().body("orders",notNullValue());

    }

}
