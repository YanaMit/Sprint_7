import courier.Courier;
import courier.CourierApi;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CourierNoParametersCreatingTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
    private static final String LOGIN = "ninja"+RandomStringUtils.randomAlphabetic(5);
    private static final String PASSWORD = "1234";
    private static final String FIRSTNAME = "saske";


        @Before
        public void setUp() {
            RestAssured.baseURI = SCOOTER_SERVICE_URI;
        }


        @Test
        public void createCourierWithoutLoginError() {
            Courier courier = new Courier(null, PASSWORD, FIRSTNAME);
            CourierApi.createCourier(courier)
                    .then().statusCode(SC_BAD_REQUEST).assertThat().
                    body("message", equalTo("Недостаточно данных для создания учетной записи"));

        }


    @Test
    public void createCourierWithoutPasswordError() {
        Courier courier = new Courier(LOGIN, null, FIRSTNAME);
        CourierApi.createCourier(courier)
                .then().statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
       }


    @Test
    public void createCourierWithoutPasswordWithoutLoginError() {

        Courier courier = new Courier(null, null, FIRSTNAME);
        CourierApi.createCourier(courier)
                .then().statusCode(SC_BAD_REQUEST).and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

}

