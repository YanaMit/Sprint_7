import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

  public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

  public static final Courier COURIER = new Courier("ninjaWR12389", "1234", "saske");

 @Before
  public void setUp() {
    RestAssured.baseURI = SCOOTER_SERVICE_URI;
  }

  @Test
  public void createCourierExpectOk() {

    given().header("Content-type", "application/json")
            .and()
            .body(COURIER.getFullJson())
            .when()
            .post("/api/v1/courier")
            .then().statusCode(201).and()
            .assertThat().body("ok", equalTo(true));

  }


  @Test
  public void createCourierWithoutFirstnameExpectOk() {
    given().header("Content-type", "application/json")
            .and()
            .body(COURIER.getNoFirstNameJson())
            .when()
            .post("/api/v1/courier")
            .then().statusCode(201).and()
            .assertThat().body("ok", equalTo(true));

  }


  @Test
  public void createSameCourierExpectError() {
    given().header("Content-type", "application/json")
            //.auth().oauth2("подставь_сюда_свой_токен")
            .and()
            .body(COURIER.getFullJson())
            .when()
            .post("/api/v1/courier")
            .then().statusCode(201);

    given().header("Content-type", "application/json")
            //.auth().oauth2("подставь_сюда_свой_токен")
            .and()
            .body(COURIER.getFullJson())
            .when()
            .post("/api/v1/courier")
            .then().statusCode(409).and()
            .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
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