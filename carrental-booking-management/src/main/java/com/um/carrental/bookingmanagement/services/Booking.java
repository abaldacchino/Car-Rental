package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.enums.BookingStatus;

import java.time.LocalDateTime;

public class Booking {
    String bookingID;

    String numberPlate;

    String customerID;

    LocalDateTime startTime;

    int hours;

    BookingStatus status;

    double totalPrice;

    public Booking() {
    }

    public Booking(String bookingID, String numberPlate, String customerID, LocalDateTime startTime, int hours, BookingStatus status, double totalPrice) {
        this.bookingID = bookingID;
        this.numberPlate = numberPlate;
        this.customerID = customerID;
        this.startTime = startTime;
        this.hours = hours;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID='" + bookingID + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", customerID='" + customerID + '\'' +
                ", startTime=" + startTime +
                ", hours=" + hours +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
