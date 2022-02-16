package com.um.carrental.bookingmanagement.web.requests;

import java.time.LocalDateTime;

public class AddBookingRequest {
    String numberPlate;
    String customerID;
    LocalDateTime startTime;
    int hours;

    public AddBookingRequest() {
    }

    public AddBookingRequest(String numberPlate, String customerID, LocalDateTime startTime, int hours) {
        this.numberPlate = numberPlate;
        this.customerID = customerID;
        this.startTime = startTime;
        this.hours = hours;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
