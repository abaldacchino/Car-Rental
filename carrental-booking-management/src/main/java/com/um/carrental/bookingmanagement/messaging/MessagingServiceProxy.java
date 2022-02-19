package com.um.carrental.bookingmanagement.messaging;

// Proxy design pattern used for Lazy Loading of communication services
public class MessagingServiceProxy implements MessagingServiceInterface{
    MessagingService messagingService = new MessagingService();
    boolean vehicleServiceInstantiated = false;
    boolean customerServiceInstantiated = false;

    public double getVehiclePrice(String numberPlate){
        if(!vehicleServiceInstantiated){
            messagingService.instantiateVehicleService();
            vehicleServiceInstantiated=true;
        }
        return messagingService.getVehiclePrice(numberPlate);
    }

    public boolean customerExistsById(String customerId){
        if(!customerServiceInstantiated){
            messagingService.instantiateCustomerService();
            customerServiceInstantiated=true;
        }
        return messagingService.customerExistsById(customerId);
    }

}
