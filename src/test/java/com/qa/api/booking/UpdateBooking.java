package com.qa.api.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.api.models.requests.Bookingdates;
import com.qa.api.models.requests.CreateBookingRequest;
import com.qa.api.models.requests.GetTokenRequest;
import com.qa.api.models.response.CreateBookingResponse;
import com.qa.api.models.response.GetTokenResponse;
import com.qa.api.models.response.UpdateBookingResponse;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateBooking {

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
    public void updateBookingTest() throws IOException {
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

        // POST - create a booking
        Bookingdates bookingdates = Bookingdates.builder()
                .checkin("2018-01-01")
                .checkout("2018-01-05").build();

        CreateBookingRequest createBookingRequest = CreateBookingRequest.builder()
                .firstname("Andrew")
                .lastname("Short")
                .totalprice(69)
                .depositpaid(true)
                .bookingdates(bookingdates)
                .additionalneeds("Breakfast")
                .build();

        APIResponse apiPostCreateBookingResponse = apiRequestContext.post(
                "https://restful-booker.herokuapp.com/booking",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Cookie", "token=" + token)
                        .setData(createBookingRequest)
        );

        ObjectMapper objectMapperForCreateBookingResponse = new ObjectMapper();
        CreateBookingResponse createBookingResponse = objectMapperForCreateBookingResponse.readValue(apiPostCreateBookingResponse.text(), CreateBookingResponse.class);
        int bookingId = createBookingResponse.getBookingid();
        System.out.println("Booking ID is - " + bookingId);

        // PUT - update a booking
        Bookingdates bookingdates2 = Bookingdates.builder()
                .checkin("2018-01-01")
                .checkout("2018-01-05").build();

        CreateBookingRequest updateBookingRequest = CreateBookingRequest.builder()
                .firstname("Fred")
                .lastname("Egg")
                .totalprice(69)
                .depositpaid(true)
                .bookingdates(bookingdates2)
                .additionalneeds("Breakfast")
                .build();

        APIResponse apiPutUpdateBookingResponse = apiRequestContext.put(
                "https://restful-booker.herokuapp.com/booking/" + bookingId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Cookie", "token=" + token)
                        .setData(updateBookingRequest)
        );

        ObjectMapper objectMapperForUpdatingBookingResponse = new ObjectMapper();
        UpdateBookingResponse updateBookingResponse = objectMapperForUpdatingBookingResponse.readValue(apiPutUpdateBookingResponse.text(), UpdateBookingResponse.class);
        Assert.assertEquals(updateBookingResponse.getFirstname(), "Fred");
        Assert.assertEquals(updateBookingResponse.getLastname(), "Egg");
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }


}
