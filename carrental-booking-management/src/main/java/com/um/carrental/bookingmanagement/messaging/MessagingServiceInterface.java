package com.um.carrental.bookingmanagement.messaging;

public interface MessagingServiceInterface {
    public boolean vehicleExistsByNumberPlate(String numberPlate);
    public boolean customerExistsById(String customerId);
}
