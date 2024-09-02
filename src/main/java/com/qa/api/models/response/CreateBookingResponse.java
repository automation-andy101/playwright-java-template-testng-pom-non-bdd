package com.qa.api.models.response;

import com.qa.api.models.requests.Bookingdates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateBookingResponse {
    private int bookingid;
    private Booking booking;
}
