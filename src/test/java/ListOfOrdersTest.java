import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import order.OrderApi;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;



public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.SCOOTER_SERVICE_URI;
    }

    @Test
    @DisplayName("Check that list of orders is not empty")
    public void listOfOrdersExpectNotEmpty() {

        OrderApi.getOrderList()
                .then().statusCode(SC_OK).and()
                .assertThat().body("orders",notNullValue());

    }

}
