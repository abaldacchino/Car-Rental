package com.um.carrental.bookingmanagement.messaging;

public class VehicleAPIResponse {
    private String numberPlate;
    private double price;

    public VehicleAPIResponse() {
    }

    public VehicleAPIResponse(String numberPlate, double price) {
        this.numberPlate = numberPlate;
        this.price = price;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VehicleAPIResponse{" +
                "numberPlate='" + numberPlate + '\'' +
                ", price=" + price +
                '}';
    }
}
