package com.um.carrental.bookingmanagement.messaging;

public interface MessagingServiceInterface {
    public double getVehiclePrice(String numberPlate);
    public boolean customerExistsById(String customerId);
}
