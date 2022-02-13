package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.data.entities.VehicleEntity;
import com.um.carrental.vehiclemanagement.data.respositories.VehicleRepository;
import com.um.carrental.vehiclemanagement.enums.VehicleType;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleManagementServiceTests {

    @Autowired
    VehicleManagementService vehicleManagementService;

    @MockBean
    VehicleRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void testAddFamilyVehicle(){
        // Setup
        VehicleSubmission vehicleSubmission = new VehicleSubmission();
        vehicleSubmission.setType(VehicleType.FAMILY);
        vehicleSubmission.setNumberPlate("DFE 123");
        boolean expectedEntered = true;

        when(repository.existsById(any(String.class))).thenReturn(false);

        // Exercise
        boolean entered = vehicleManagementService.addVehicle(vehicleSubmission);

        // Verify

        assertNotNull(entered, "Response is null.");
        assertEquals(expectedEntered, entered);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .save is called once
        verify(repository, times(1)).save(any(VehicleEntity.class));
        // Teardown -- no teardown needed
    }

    @Test
    public void testAddMotorcycleVehicle(){
        // Setup
        VehicleSubmission vehicleSubmission = new VehicleSubmission();
        vehicleSubmission.setType(VehicleType.MOTORCYCLE);
        vehicleSubmission.setNumberPlate("DFE 123");
        boolean expectedEntered = true;

        when(repository.existsById(any(String.class))).thenReturn(false);

        // Exercise
        boolean entered = vehicleManagementService.addVehicle(vehicleSubmission);

        // Verify

        assertNotNull(entered, "Response is null.");
        assertEquals(expectedEntered, entered);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .save is called once
        verify(repository, times(1)).save(any(VehicleEntity.class));
        // Teardown -- no teardown needed
    }

    @Test
    public void testAddCommercialVehicle(){
        // Setup
        VehicleSubmission vehicleSubmission = new VehicleSubmission();
        vehicleSubmission.setType(VehicleType.COMMERCIAL);
        vehicleSubmission.setNumberPlate("DFE 123");
        boolean expectedEntered = true;

        when(repository.existsById(any(String.class))).thenReturn(false);

        // Exercise
        boolean entered = vehicleManagementService.addVehicle(vehicleSubmission);

        // Verify

        assertNotNull(entered, "Response is null.");
        assertEquals(expectedEntered, entered);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .save is called once
        verify(repository, times(1)).save(any(VehicleEntity.class));
        // Teardown -- no teardown needed
    }

    @Test
    public void testAddExistingVehicle(){
        // Setup
        VehicleSubmission vehicleSubmission = new VehicleSubmission();
        vehicleSubmission.setType(VehicleType.FAMILY);
        vehicleSubmission.setNumberPlate("DFE 123");
        boolean expectedEntered = false;

        FamilyCar createdFamilyCar = new FamilyCar(vehicleSubmission.getNumberPlate());
        VehicleEntity passedVehicleEntity = modelMapper.map(createdFamilyCar, VehicleEntity.class);

        //existsById returns true meaning another vehicle already has the same ID
        when(repository.existsById(any(String.class))).thenReturn(true);

        // Exercise
        boolean entered = vehicleManagementService.addVehicle(vehicleSubmission);

        // Verify

        assertNotNull(entered, "Response is null.");
        assertEquals(expectedEntered, entered);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .save is not called
        verify(repository, times(0)).save(any(VehicleEntity.class));
        // Teardown -- no teardown needed
    }

    @Test
    public void deletePresentVehicleTest(){
        // Setup
        String numberPlate = "ABC 123";
        //existsById returns true meaning vehicle exists in repository
        when(repository.existsById(any(String.class))).thenReturn(true);
        boolean expectedFound = true;

        // Exercise
        boolean found = vehicleManagementService.deleteVehicle(numberPlate);

        // Verify
        assertEquals(expectedFound, found);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .delete is called once
        verify(repository, times(1)).deleteById(any(String.class));

        // Teardown -- no teardown needed
    }

    @Test
    public void deleteNotPresentVehicleTest(){
        // Setup
        String numberPlate = "ABC 123";
        //existsById returns true meaning vehicle exists in repository
        when(repository.existsById(any(String.class))).thenReturn(false);
        boolean expectedFound = false;

        // Exercise
        boolean found = vehicleManagementService.deleteVehicle(numberPlate);

        // Verify
        assertEquals(expectedFound, found);

        //Checking that .existsById is called once
        verify(repository, times(1)).existsById(any(String.class));
        //Checking that .delete is not called
        verify(repository, times(0)).delete(any(VehicleEntity.class));

        // Teardown -- no teardown needed
    }

    @Test
    public void getPresentVehicleById(){
        // Setup

        String numberPlate = "ABC 123";
        Vehicle expectedResponse = new Vehicle(numberPlate, VehicleType.FAMILY, 20, 10);
        VehicleEntity expectedEntityResponse = modelMapper.map(expectedResponse, VehicleEntity.class);
        when(repository.existsById(numberPlate)).thenReturn(true);
        when(repository.getById(numberPlate)).thenReturn(expectedEntityResponse);

        // Exercise
        Vehicle response = vehicleManagementService.getVehicleByNumberPlate(numberPlate);

        // Verify

        assertTrue(expectedResponse.equals(response));
        verify(repository, times(1)).existsById(numberPlate);
        verify(repository, times(1)).getById(numberPlate);

        // Teardown -- no teardown needed
    }

}