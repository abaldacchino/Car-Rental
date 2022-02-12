package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

import java.util.Objects;

public class Vehicle {
    String numberPlate;
    VehicleType vehicleType;
    double price;
    int capacity;

    public Vehicle(String numberPlate, VehicleType vehicleType, double price, int capacity) {
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.price = price;
        this.capacity = capacity;
    }

    public Vehicle() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.price, price) == 0 && capacity == vehicle.capacity && numberPlate.equals(vehicle.numberPlate) && vehicleType == vehicle.vehicleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberPlate);
    }
}

