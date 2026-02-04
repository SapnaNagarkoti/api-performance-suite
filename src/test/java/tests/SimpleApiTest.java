package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class SimpleApiTest {

    private static final long SLA_MS = 2000;

    @Test
    @Description("Verify API response time against SLA")
    public void userApiPerformanceTest() {

        long responseTime = getUserResponseTime();

        System.out.println("Response time: " + responseTime + " ms");

        if (responseTime > SLA_MS) {
            throw new AssertionError(
                    "SLA breached! Response time = " + responseTime + " ms"
            );
        }
    }

    @Step("Calling GET /api/users/2 and measuring response time")
    private long getUserResponseTime() {

        return given()
                .header("User-Agent", "Mozilla/5.0")
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .extract()
                .time();
    }
}
