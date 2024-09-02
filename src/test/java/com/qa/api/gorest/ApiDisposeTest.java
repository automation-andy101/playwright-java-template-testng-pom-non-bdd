package com.qa.api.gorest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class ApiDisposeTest {
    Playwright playwright;
    APIRequest request;
    APIRequestContext apiRequestContext;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        apiRequestContext = request.newContext();
    }

    @Test
    public void disposeResponseTest() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");

        int statusCode = apiResponse.status();
        System.out.println("Response status code is - " + statusCode);

        String statusText = apiResponse.statusText();
        System.out.println("Response status text is - " + statusText);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonNode.toPrettyString();
        System.out.println("Response body is - " + jsonPrettyResponse);

        Map<String, String> headersMap = apiResponse.headers();
        Assert.assertEquals(headersMap.get("content-type"), "application/json");
        System.out.println(headersMap);

        apiResponse.dispose();
        apiRequestContext.dispose();
    }


    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
