package order;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;



public class OrderApi {
    public static final String ORDER_ENDPOINT = "/api/v1/orders";

    public static final String CANCEL_ORDER_ENDPOINT = "/api/v1/orders/cancel";


    @Step("Make order")
    public static Response createOrder(Order order) {
        Response response =  given().header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
        return response;
    }

    @Step("Get list of orders")
    public static Response getOrderList(){
      Response response = given().header("Content-type", "application/json")
              .and()
              .when()
              .get(ORDER_ENDPOINT);
      return response;
    }

    @Step("Cancel order")
    public static void cancelOrder(Integer trackNumber) {
        if (trackNumber != null) {
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(trackNumber)
                    .when()
                    .delete(CANCEL_ORDER_ENDPOINT + trackNumber);
        }
    }

}




