package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.messaging.MessagingServiceInterface;

public class MessagingServiceMock implements MessagingServiceInterface {
    double returnVarGetVehiclePrice;
    boolean returnVarCustomerExistsById;

    public MessagingServiceMock(double returnVarGetVehiclePrice, boolean returnVarCustomerExistsById) {
        this.returnVarGetVehiclePrice = returnVarGetVehiclePrice;
        this.returnVarCustomerExistsById = returnVarCustomerExistsById;
    }

    @Override
    public double getVehiclePrice(String numberPlate) {
        return returnVarGetVehiclePrice;
    }

    @Override
    public boolean customerExistsById(String customerId) {
        return returnVarCustomerExistsById;
    }
}
