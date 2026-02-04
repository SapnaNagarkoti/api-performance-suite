package tests;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.ApiConfig;
import org.testng.annotations.Test;
import utils.ApiLoader;
import utils.RequestBuilder;

import java.util.List;

public class ApiPerformanceTest {

    @Test
    @Description("API response time ")
    public void apiPerformanceSuite() {

        List<ApiConfig> apis = ApiLoader.loadApis();

        for (ApiConfig api : apis) {

            Response response =
                    RequestBuilder
                            .build(api.auth)
                            .when()
                            .request(api.method, api.url);

            long time = response.time();

            System.out.println("API: " + api.name);
            System.out.println("Status: " + response.statusCode());
            System.out.println("Time: " + time + " ms");
            System.out.println("Body: " + response.getBody().asString());

            attachResponse(api.name, response.getBody().asString());


            if (response.statusCode() != api.expectedStatus) {
                throw new AssertionError("Expected " + api.expectedStatus + " but got " + response.statusCode());
            }

            if (time > api.sla) {
                System.out.println("⚠ SLA breached for " + api.name);
            }
        }
    }

    @Attachment(value = "{apiName} Response", type = "application/json")
    public String attachResponse(String apiName, String body) {
        return body;
    }
}
