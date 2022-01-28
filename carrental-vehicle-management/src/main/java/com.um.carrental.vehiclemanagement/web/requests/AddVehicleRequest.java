package com.um.carrental.vehiclemanagement.web.requests;

import com.um.carrental.vehiclemanagement.web.enums.VehicleType;

public class AddVehicleRequest {
    private String numberPlate;
    private VehicleType type;

    public AddVehicleRequest() {
    }

    public AddVehicleRequest(String numberPlate, VehicleType type) {
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
