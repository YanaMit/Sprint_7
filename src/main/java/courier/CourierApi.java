package courier;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;



public class CourierApi

{
    public static final String COURIER_ENDPOINT = "/api/v1/courier";
    public static final String LOGIN_ENDPOINT = "/api/v1/courier/login";


    @Step("Create courier")
    public static Response createCourier(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_ENDPOINT);
        return response;
    }

    @Step("Courier log in")
    public static Response logInCourier(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_ENDPOINT);
        return response;
    }

    @Step("Delete courier")
    public static void deleteCourier(String id) {
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(id)
                    .when()
                    .delete(LOGIN_ENDPOINT + id);
    }


}
