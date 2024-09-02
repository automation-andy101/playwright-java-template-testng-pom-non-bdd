package com.qa.api.booking;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.models.requests.GetTokenRequest;
import com.qa.api.models.response.GetTokenResponse;
import com.qa.api.models.response.UsersResponse;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TokenAuth {

    Playwright playwright;
    APIRequest request;
    APIRequestContext apiRequestContext;
    static String emailId;

    @BeforeTest
    public void setup() {
        playwright = Playwright.create();
        request = playwright.request();
        apiRequestContext = request.newContext();
    }

    @Test
    public void getTokenTest() throws IOException {

        // POST - Get a token
        GetTokenRequest getTokenRequestBody = GetTokenRequest.builder()
                .username("admin")
                .password("password123")
                .build();

        APIResponse apiPostResponse = apiRequestContext.post(
                "https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(getTokenRequestBody)
        );

        Assert.assertEquals(apiPostResponse.status(), 200);
        Assert.assertEquals(apiPostResponse.statusText(), "OK");

        ObjectMapper objectMapper = new ObjectMapper();
        GetTokenResponse getTokenResponse = objectMapper.readValue(apiPostResponse.text(), GetTokenResponse.class);
        String token = getTokenResponse.getToken();
        System.out.println("token value is - " + token);
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }


}
