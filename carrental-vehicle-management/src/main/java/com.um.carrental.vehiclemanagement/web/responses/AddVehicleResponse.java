package com.um.carrental.vehiclemanagement.web.responses;

public class AddVehicleResponse {
    boolean entered;

    public AddVehicleResponse() {
    }

    public AddVehicleResponse(boolean entered) {
        this.entered = entered;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }
}
