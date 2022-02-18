package com.um.carrental.bookingmanagement.messaging;

// Proxy design pattern used for Lazy Loading of communication services
public class MessagingServiceProxy implements MessagingServiceInterface{
    MessagingService messagingService = new MessagingService();
    boolean vehicleServiceInstantiated = false;
    boolean customerServiceInstantiated = false;

    public boolean vehicleExistsByNumberPlate(String numberPlate){
        if(!vehicleServiceInstantiated){
            messagingService.instantiateVehicleService();
            vehicleServiceInstantiated=true;
        }
        return messagingService.vehicleExistsByNumberPlate(numberPlate);
    }

    public boolean customerExistsById(String customerId){
        return true;
    }

}
