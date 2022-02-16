package com.um.carrental.bookingmanagement.web.responses;

import com.um.carrental.bookingmanagement.enums.BookingStatus;

public class AddBookingResponse {
    String bookingID;
    BookingStatus status;

    public AddBookingResponse() {
    }

    public AddBookingResponse(String bookingID, BookingStatus status) {
        this.bookingID = bookingID;
        this.status = status;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
