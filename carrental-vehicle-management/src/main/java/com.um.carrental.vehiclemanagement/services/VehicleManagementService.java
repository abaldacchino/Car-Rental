package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.data.entities.VehicleEntity;
import com.um.carrental.vehiclemanagement.data.respositories.VehicleRepository;
import com.um.carrental.vehiclemanagement.enums.RequestType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleManagementService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    VehicleRepository repository;

    public boolean addVehicle(VehicleSubmission vehicleSubmission) {
        Vehicle vehicle;
        switch(vehicleSubmission.getType()) {
            case FAMILY:
                vehicle = new FamilyCar(vehicleSubmission.getNumberPlate());
                break;
            case COMMERCIAL:
                vehicle = new CommercialVehicle((vehicleSubmission.getNumberPlate()));
                break;
            case MOTORCYCLE:
                vehicle = new Motorcycle(vehicleSubmission.getNumberPlate());
                break;
            default:
                return false;
        }
        //Call repository to save data
        VehicleEntity vehicleEntity = mapper.map(vehicle, VehicleEntity.class);
        boolean exists = repository.existsById(vehicleEntity.getNumberPlate());

        if(exists){
            return false;
        }else{
            repository.save(vehicleEntity);
            return true;
        }
    }

    public boolean deleteVehicle(String numberPlate){
        if(!repository.existsById(numberPlate))
            return false;
        repository.deleteById(numberPlate);
        return true;
    }

    public Vehicle getVehicleByNumberPlate(String numberPlate){
        if(repository.existsById(numberPlate)){
            return mapper.map(repository.getById(numberPlate), Vehicle.class);
        }
        return null;
    }

    public List<Vehicle> getVehicleByCapacity(int capacity, RequestType requestType){

        return null;
    }

    public List<Vehicle> getVehicleByPrice(double price, RequestType requestType){

        return null;
    }
}
