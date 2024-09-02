package com.qa.api.gorest.post;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApiPostCreateUserWithJsonFile {
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
        byte[] fileBytes = null;
        File file = new File("./src/test/data/user.json");
        fileBytes = Files.readAllBytes(file.toPath());

        String reqJsonBody = "src/test/data/user.json";

        String token = "a15dc42c77e1617b283e6c444406f501a43a0eb627328c141aad80efbb07e812";

        APIResponse apiPostResponse = apiRequestContext.post(
                "https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer " + token)
                        .setData(fileBytes)
        );

        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

        // GET the previously created user
        String userId = postJsonResponse.get("id").asText();
        APIResponse apiGetResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer " + token)
        );

        Assert.assertEquals(apiGetResponse.status(), 200);
        Assert.assertEquals(apiGetResponse.statusText(), "OK");
        Assert.assertTrue(apiGetResponse.text().contains(userId));
        Assert.assertTrue(apiGetResponse.text().contains(emailId));
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
