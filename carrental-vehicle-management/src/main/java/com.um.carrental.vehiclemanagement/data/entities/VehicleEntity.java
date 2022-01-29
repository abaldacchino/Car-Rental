package com.um.carrental.vehiclemanagement.data.entities;

import com.um.carrental.vehiclemanagement.enums.VehicleType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class VehicleEntity {
    @Id
    private String numberPlate;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private double price;

    private int capacity;

    public VehicleEntity(String numberPlate, VehicleType vehicleType, double price, int capacity) {
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.price = price;
        this.capacity = capacity;
    }

    public VehicleEntity() {
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
