package utils;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthUtil {

    private static Cookies cookies;

    public static Cookies getCookies() {
        if (cookies == null) {
            cookies = loginAndGetCookies();
        }
        return cookies;
    }

    private static Cookies loginAndGetCookies() {

        Response response =
                given()
                        .baseUri("https://secure.yatra.com")
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("emailId", "username")
                        .formParam("password", "password")
                        .formParam("captchaParam", "")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .when()
                        .post("/social/custom/crp/doLogin.htm");

        System.out.println("Login status = " + response.statusCode());
        System.out.println("Login body = " + response.getBody().asString());
        System.out.println("Cookies = " + response.getDetailedCookies());

        if (response.statusCode() != 200 || response.getDetailedCookies().asList().isEmpty()) {
            throw new RuntimeException("Login failed, cannot get cookies");
        }

        return response.getDetailedCookies();
    }


}
