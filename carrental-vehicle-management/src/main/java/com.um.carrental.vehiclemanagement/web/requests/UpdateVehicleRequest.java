package com.um.carrental.vehiclemanagement.web.requests;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

public class UpdateVehicleRequest {
    String numberPlate;
    VehicleType vehicleType;
    double price;
    int capacity;

    public UpdateVehicleRequest(String numberPlate, VehicleType vehicleType, double price, int capacity) {
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.price = price;
        this.capacity = capacity;
    }

    public UpdateVehicleRequest() {
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
