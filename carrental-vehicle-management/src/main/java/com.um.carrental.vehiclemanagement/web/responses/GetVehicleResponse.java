package com.um.carrental.vehiclemanagement.web.responses;

import com.um.carrental.vehiclemanagement.services.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class GetVehicleResponse {
    List<Vehicle> vehicles;

    public GetVehicleResponse() {
    }

    public GetVehicleResponse(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
