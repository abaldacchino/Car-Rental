package com.um.carrental.vehiclemanagement.web.responses;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class GetVehicleByNumberPlateResponse {
    String numberPlate;
    VehicleType vehicleType;
    double price;
    int capacity;

    public GetVehicleByNumberPlateResponse(String numberPlate, VehicleType vehicleType, double price, int capacity) {
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.price = price;
        this.capacity = capacity;
    }

    public GetVehicleByNumberPlateResponse() {
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
