package com.um.carrental.bookingmanagement.web.requests;

public class GetBookingRequest {
    String bookingID;

    public GetBookingRequest() {
    }

    public GetBookingRequest(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }
}
