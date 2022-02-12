package com.um.carrental.vehiclemanagement.web.requests;

public class GetVehicleByIdRequest {
    String numberPlate;

    public GetVehicleByIdRequest() {
    }

    public GetVehicleByIdRequest(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }
}
