package com.qa.api.gorest.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.models.requests.UsersRequest;
import com.qa.api.models.response.UsersResponse;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.qa.api.gorest.post.ApiPostCreateAUserTest.getRandomEmail;

public class ApiPutUpdateUserWithPojoLombok {
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
    public void updateUserTest() throws IOException {

//        1. POST - Create a user
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

        // 2. PUT - Update a user
        String userId = usersResponse.getId();
        usersRequest.setStatus("inactive");

        APIResponse apiPutResponse = apiRequestContext.put(
                "https://gorest.co.in/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(usersRequest)
        );

        Assert.assertEquals(apiPutResponse.status(), 200);
        UsersResponse usersPUTResponse = objectMapper.readValue(apiPutResponse.text(), UsersResponse.class);
        Assert.assertEquals(usersPUTResponse.getId(), userId);
        Assert.assertEquals(usersPUTResponse.getStatus(), usersRequest.getStatus());

        // 3. GET - Get user
        APIResponse apiGetResponse = apiRequestContext.get(
                "https://gorest.co.in/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertEquals(apiGetResponse.status(), 200);
        UsersResponse usersGETResponse = objectMapper.readValue(apiGetResponse.text(), UsersResponse.class);
        Assert.assertEquals(usersGETResponse.getStatus(), usersRequest.getStatus());
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
