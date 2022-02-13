package com.um.carrental.vehiclemanagement.web.controllers;

import com.um.carrental.vehiclemanagement.enums.RequestType;
import com.um.carrental.vehiclemanagement.exceptions.VehicleNotFoundException;
import com.um.carrental.vehiclemanagement.services.Vehicle;
import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.DeleteVehicleRequest;
import com.um.carrental.vehiclemanagement.web.responses.AddVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.DeleteVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.GetVehicleByNumberPlateResponse;
import com.um.carrental.vehiclemanagement.web.responses.GetVehicleResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleManagementController {

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

    // Delete a vehicle
    // Method --> DELETE
    // Request --> DeleteVehicleRequest
    // Response --> 200 (OK), DeleteVehicleResponse
    @DeleteMapping(value = "vehicles/{numberPlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DeleteVehicleResponse deleteVehicle(@RequestBody DeleteVehicleRequest request){
        return new DeleteVehicleResponse(vehicleManagementService.deleteVehicle(request.getNumberPlate()));
    }

    // Get a vehicle by ID (numberPlate)
    // Method --> GET
    // Request --> numberPlate
    // Response --> 200 (OK), GetVehicleByIdResponse
    @GetMapping(value = "vehicles/{numberPlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleByNumberPlateResponse getVehicleByNumberPlate(@PathVariable String numberPlate){
        Vehicle returnedVehicle = vehicleManagementService.getVehicleByNumberPlate(numberPlate);
        if(returnedVehicle == null)throw new VehicleNotFoundException();
        return mapper.map(returnedVehicle, GetVehicleByNumberPlateResponse.class);
    }

    // Get a vehicle by various attributes
    // Method --> GET
    // Request --> GetVehicleRequest
    // Response --> 200 (OK), GetVehicleResponse
    @GetMapping(value = "vehicles/{capacity}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleResponse getVehicleByCapacity(@PathVariable int capacity,
                                         @PathVariable RequestType requestType){
        List<Vehicle> matchingVehicles =
                vehicleManagementService.getVehicleByCapacity(capacity, requestType);
        if(matchingVehicles.isEmpty()){
            throw new VehicleNotFoundException();
        }else return new GetVehicleResponse(matchingVehicles);
    }

}
