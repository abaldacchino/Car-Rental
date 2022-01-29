package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class VehicleSubmission {
    private String numberPlate;
    private VehicleType type;

    public VehicleSubmission() {
    }

    public VehicleSubmission(String numberPlate, VehicleType type) {
        this.numberPlate = numberPlate;
        this.type = type;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}
