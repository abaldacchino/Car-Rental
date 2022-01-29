package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class CommercialVehicle extends Vehicle{
    public CommercialVehicle(String numberPlate) {
        super(numberPlate, VehicleType.COMMERCIAL, 140, 8);
    }
}
