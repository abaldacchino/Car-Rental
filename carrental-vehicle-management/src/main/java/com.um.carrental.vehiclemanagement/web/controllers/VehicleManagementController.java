package com.um.carrental.vehiclemanagement.web.controllers;

import com.um.carrental.vehiclemanagement.enums.RequestType;
import com.um.carrental.vehiclemanagement.enums.VehicleType;
import com.um.carrental.vehiclemanagement.exceptions.VehicleNotFoundException;
import com.um.carrental.vehiclemanagement.services.Vehicle;
import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.DeleteVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.UpdateVehicleRequest;
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
    // Response --> 200 (OK), GetVehicleByNumberPlateResponse
    @GetMapping(value = "vehicles/{numberPlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleByNumberPlateResponse getVehicleByNumberPlate(
            @RequestParam(value="numberPlate") String numberPlate){
        Vehicle returnedVehicle = vehicleManagementService.getVehicleByNumberPlate(numberPlate);
        if(returnedVehicle == null)throw new VehicleNotFoundException();
        GetVehicleByNumberPlateResponse response =
                mapper.map(returnedVehicle, GetVehicleByNumberPlateResponse.class);
        System.out.println(response.getNumberPlate());
        return response;
    }

    // Get a vehicle by capacity
    // Method --> GET
    // Request --> capacity, requestType
    // Response --> 200 (OK), GetVehicleResponse
    @GetMapping(value = "vehicles/{capacity}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleResponse getVehicleByCapacity(@RequestParam(value="capacity") int capacity,
                                                   @RequestParam(value="requestType") RequestType requestType){
        List<Vehicle> matchingVehicles =
                vehicleManagementService.getVehicleByCapacity(capacity, requestType);
        if(matchingVehicles.isEmpty()){
            throw new VehicleNotFoundException();
        }else return new GetVehicleResponse(matchingVehicles);
    }

    // Get a vehicle by price
    // Method --> GET
    // Request --> capacity, requestType
    // Response --> 200 (OK), GetVehicleResponse
    @GetMapping(value = "vehicles/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleResponse getVehicleByPrice(@RequestParam(value="price") double price,
                                                @RequestParam(value="requestType") RequestType requestType){
        List<Vehicle> matchingVehicles =
                vehicleManagementService.getVehicleByPrice(price, requestType);
        if(matchingVehicles.isEmpty()){
            throw new VehicleNotFoundException();
        }else return new GetVehicleResponse(matchingVehicles);
    }

    // Get a vehicle by vehicle type
    // Method --> GET
    // Request --> capacity, requestType
    // Response --> 200 (OK), GetVehicleResponse
    @GetMapping(value = "vehicles/{vehicleType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetVehicleResponse getVehicleByVehicleType(@RequestParam(value="vehicleType") VehicleType vehicleType){
        List<Vehicle> matchingVehicles =
                vehicleManagementService.getVehicleByVehicleType(vehicleType);
        if(matchingVehicles.isEmpty()){
            throw new VehicleNotFoundException();
        }else return new GetVehicleResponse(matchingVehicles);
    }

    // Update a vehicle
    // Method --> PUT
    // Request --> UpdateVehicleRequest
    // Response --> 204 (No Content)
    @PutMapping(value = "vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVehicle(@RequestBody UpdateVehicleRequest request){
        boolean found = vehicleManagementService.updateVehicle(request);
        if(!found) throw new VehicleNotFoundException();
    }

}
