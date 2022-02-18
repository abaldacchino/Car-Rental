package com.um.carrental.bookingmanagement.web.responses;

import com.um.carrental.bookingmanagement.services.Booking;

import java.util.List;

public class GetBookingListResponse {
    List<Booking> bookings;

    public GetBookingListResponse() {
    }

    public GetBookingListResponse(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
