package com.um.carrental.vehiclemanagement.web.responses;

public class DeleteVehicleResponse {
    boolean found;

    public DeleteVehicleResponse() {
    }

    public DeleteVehicleResponse(boolean found) {
        this.found = found;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
