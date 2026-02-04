package utils;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestBuilder {

    public static RequestSpecification build(boolean authRequired) {

        RequestSpecification req = given()
                .header("User-Agent", "Mozilla/5.0")
                .relaxedHTTPSValidation();

        if (authRequired) {
            req.cookies(AuthUtil.getCookies());
        }

        return req;
    }
}
