package com.um.carrental.bookingmanagement.messaging;

public class VehicleAPIResponse {
    private String numberPlate;

    public VehicleAPIResponse() {
    }

    public VehicleAPIResponse(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    @Override
    public String toString() {
        return "VehicleAPIResponse{" +
                "numberPlate='" + numberPlate + '\'' +
                '}';
    }
}
