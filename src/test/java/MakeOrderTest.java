import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.Order;
import order.OrderApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;


@RunWith(Parameterized.class)

public class MakeOrderTest {

    private List<String> color;
    Integer trackNumber;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.SCOOTER_SERVICE_URI;
    }

    @Parameterized.Parameters
    public static Object[][] chooseColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }

    public MakeOrderTest(List<String> color) {
        this.color = color;
    }

    @Test
    @DisplayName("Create orders with different colors")
    public void createOrderExpectOk() {

        Order order = new Order("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "Сокол",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        Response response =  OrderApi.createOrder(order);

        trackNumber = response.then().extract().path("track");

        response.then().statusCode(SC_CREATED).and()
                .assertThat().body("track", notNullValue());

    }

    @After
    public void cancelOrder() {
        OrderApi.cancelOrder(trackNumber);
    }

}
