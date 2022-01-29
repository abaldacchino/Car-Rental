package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class Motorcycle extends Vehicle{
    public Motorcycle(String numberPlate) {
        super(numberPlate, VehicleType.MOTORCYCLE, 40, 2);
    }
}
