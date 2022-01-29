package com.um.carrental.vehiclemanagement.web.controllers;

import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.responses.AddVehicleResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddVehicleController {

    @Autowired
    VehicleManagementService vehicleManagementService;

    @Autowired
    ModelMapper mapper;

    // Create a vehicle
    // Method --> POST
    // Request --> AddVehicleRequest
    // Response --> 201 (Created), AddVehicleResponse
    @PostMapping(value = "vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AddVehicleResponse addVehicle(@RequestBody AddVehicleRequest request){
        VehicleSubmission vehicleSubmission = mapper.map(request, VehicleSubmission.class);

        return new AddVehicleResponse(vehicleManagementService.addVehicle(vehicleSubmission));
    }
}
