package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class FamilyCar extends Vehicle{
    public FamilyCar(String numberPlate) {
        super(numberPlate, VehicleType.FAMILY, 100, 5);
    }
}
