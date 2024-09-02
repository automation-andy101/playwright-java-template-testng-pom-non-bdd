package com.qa.api.gorest.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.models.requests.UserRequest;
import com.qa.api.models.response.UserResponse;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.qa.api.gorest.post.ApiPostCreateAUserTest.getRandomEmail;

public class ApiPostCreateUserWithPojo {
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
    public void createUserTest() throws IOException {
        UserRequest userRequest = new UserRequest("Andrew", getRandomEmail(), "Male", "active");

        String token = "a15dc42c77e1617b283e6c444406f501a43a0eb627328c141aad80efbb07e812";

        APIResponse apiPostResponse = apiRequestContext.post(
                "https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(userRequest)
        );

        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        // Deserialization - convert response to POJO
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponse userResponse = objectMapper.readValue(apiPostResponse.text(), UserResponse.class);

        Assert.assertEquals(userResponse.getName(), userRequest.getName());
        Assert.assertEquals(userResponse.getEmail(), userRequest.getEmail());
        Assert.assertEquals(userResponse.getGender(), userRequest.getGender());
        Assert.assertEquals(userResponse.getStatus(), userRequest.getStatus());
        Assert.assertNotNull(userResponse.getId());
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
