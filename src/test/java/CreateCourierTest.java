import courier.Courier;
import courier.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    String id;
    String login = "login"+RandomStringUtils.randomAlphabetic(5);
    String password = "password";
    String firstName = "firstName";

 @Before
  public void setUp() {
     RestAssured.baseURI = BaseURI.SCOOTER_SERVICE_URI;
  }

  @Test
  @DisplayName("Create courier with correct login, password and firstName, result ok")
  public void createCourierExpectOk() {

     Courier courier = new Courier(login, password, firstName);

     CourierApi.createCourier(courier)
            .then().statusCode(SC_CREATED).and()
            .assertThat().body("ok", equalTo(true));

      id = CourierApi.logInCourier(courier).then().extract().path("id").toString();

  }


  @Test
  @DisplayName("Create courier with correct login, password and without firstname, result ok")
  public void createCourierWithoutFirstnameExpectOk() {

      Courier courier = new Courier(login, password);

      CourierApi.createCourier(courier)
            .then().statusCode(SC_CREATED).and()
            .assertThat().body("ok", equalTo(true));

      id = CourierApi.logInCourier(courier).then().extract().path("id").toString();

  }


  @Test
  @DisplayName("Create courier with the same login, result error")
  public void createSameCourierExpectError() {

      Courier courier = new Courier(login, password);
      CourierApi.createCourier(courier)
              .then().statusCode(SC_CREATED);

      id = CourierApi.logInCourier(courier).then().extract().path("id").toString();

      CourierApi.createCourier(courier)
            .then().statusCode(SC_CONFLICT).and()
            .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

  }

  @After
  public void deleteUser() {
    CourierApi.deleteCourier(id);
  }

}