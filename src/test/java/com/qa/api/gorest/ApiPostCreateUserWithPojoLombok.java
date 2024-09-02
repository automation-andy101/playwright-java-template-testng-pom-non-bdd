package com.qa.api.gorest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.models.requests.UserRequest;
import com.qa.api.models.requests.UsersRequest;
import com.qa.api.models.response.UserResponse;
import com.qa.api.models.response.UsersResponse;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.qa.api.gorest.ApiPostCreateAUserTest.getRandomEmail;

public class ApiPostCreateUserWithPojoLombok {
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
        UsersRequest usersRequest = UsersRequest.builder()
                .name("Andrew")
                .email(getRandomEmail())
                .gender("Male")
                .status("active")
                .build();

        String token = "a15dc42c77e1617b283e6c444406f501a43a0eb627328c141aad80efbb07e812";

        APIResponse apiPostResponse = apiRequestContext.post(
                "https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(usersRequest)
        );

        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        // Deserialization - convert response to POJO
        ObjectMapper objectMapper = new ObjectMapper();
        UsersResponse usersResponse = objectMapper.readValue(apiPostResponse.text(), UsersResponse.class);

        Assert.assertEquals(usersResponse.getName(), usersRequest.getName());
        Assert.assertEquals(usersResponse.getEmail(), usersRequest.getEmail());
        Assert.assertEquals(usersResponse.getGender(), usersRequest.getGender());
        Assert.assertEquals(usersResponse.getStatus(), usersRequest.getStatus());
        Assert.assertNotNull(usersResponse.getId());
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
