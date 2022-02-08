package com.um.carrental.vehiclemanagement.web.controllers;

import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.DeleteVehicleRequest;
import com.um.carrental.vehiclemanagement.web.responses.AddVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.DeleteVehicleResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteVehicleController {

    @Autowired
    VehicleManagementService vehicleManagementService;

    // Delete a vehicle
    // Method --> DELETE
    // Request --> DeleteVehicleRequest
    // Response --> 200 (OK), DeleteVehicleResponse
    @DeleteMapping(value = "vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DeleteVehicleResponse addVehicle(@RequestBody DeleteVehicleRequest request){
        return new DeleteVehicleResponse(vehicleManagementService.deleteVehicle(request.getNumberPlate()));
    }
}
