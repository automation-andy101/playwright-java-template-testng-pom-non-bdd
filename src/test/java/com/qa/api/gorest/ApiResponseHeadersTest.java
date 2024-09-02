package com.qa.api.gorest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiResponseHeadersTest {
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
    public void getHeadersTest() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");

        // User Map
        Map<String, String> headerMap = apiResponse.headers();
        headerMap.forEach((k, v) -> System.out.println(k + ":" + v));
        Assert.assertEquals(headerMap.get("server"), "cloudflare");

        // Using List
        List<HttpHeader> headerList = apiResponse.headersArray();
        for (HttpHeader e : headerList) {
            System.out.println(e.name + " : " + e.value);
        }
    }


    @AfterTest
    public void tearDown() {
        playwright.close();
    }

}
