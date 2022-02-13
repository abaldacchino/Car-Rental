package com.um.carrental.vehiclemanagement.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.vehiclemanagement.enums.RequestType;
import com.um.carrental.vehiclemanagement.enums.VehicleType;
import com.um.carrental.vehiclemanagement.exceptions.VehicleNotFoundException;
import com.um.carrental.vehiclemanagement.services.Vehicle;
import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.controllers.VehicleManagementController;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.DeleteVehicleRequest;
import com.um.carrental.vehiclemanagement.web.responses.AddVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.DeleteVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.GetVehicleByNumberPlateResponse;
import com.um.carrental.vehiclemanagement.web.responses.GetVehicleResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleManagementControllerTests {
    @Autowired
    VehicleManagementController vehicleManagementController;

    @Autowired
    ModelMapper mapper = new ModelMapper();

    @MockBean
    VehicleManagementService vehicleManagementServiceMock;

    @Test
    public void testAddCommercialVehicle(){
        // Setup

        AddVehicleRequest request =
                new AddVehicleRequest("ABC123", VehicleType.COMMERCIAL);
        boolean expectedEntered = true;
        when(vehicleManagementServiceMock.addVehicle(any(VehicleSubmission.class))).thenReturn(expectedEntered);

        // Exercise
        AddVehicleResponse actualResponse = vehicleManagementController.addVehicle(request);

        // Verify

        assertNotNull(actualResponse, "Response is null.");
        assertEquals(expectedEntered, actualResponse.isEntered());

        verify(vehicleManagementServiceMock, times(1)).addVehicle(any(VehicleSubmission.class));
        // Teardown - no teardown stage
    }

    @Test
    public void testAddFamilyVehicle(){
        // Setup

        AddVehicleRequest request =
                new AddVehicleRequest("ABC123", VehicleType.FAMILY);
        boolean expectedEntered = true;
        when(vehicleManagementServiceMock.addVehicle(any(VehicleSubmission.class))).thenReturn(expectedEntered);

        // Exercise
        AddVehicleResponse actualResponse = vehicleManagementController.addVehicle(request);

        // Verify

        assertNotNull(actualResponse, "Response is null.");
        assertEquals(expectedEntered, actualResponse.isEntered());

        verify(vehicleManagementServiceMock, times(1)).addVehicle(any(VehicleSubmission.class));
        // Teardown - no teardown stage
    }

    @Test
    public void testAddMotorcycleVehicle(){
        // Setup

        AddVehicleRequest request =
                new AddVehicleRequest("ABC123", VehicleType.MOTORCYCLE);
        boolean expectedEntered = true;
        when(vehicleManagementServiceMock.addVehicle(any(VehicleSubmission.class))).thenReturn(expectedEntered);

        // Exercise
        AddVehicleResponse actualResponse = vehicleManagementController.addVehicle(request);

        // Verify

        assertNotNull(actualResponse, "Response is null.");
        assertEquals(expectedEntered, actualResponse.isEntered());

        verify(vehicleManagementServiceMock, times(1)).addVehicle(any(VehicleSubmission.class));
        // Teardown - no teardown stage
    }

    @Test
    public void testDeletePresentVehicle(){
        // Setup
        String numberPlate = "ABC123";
        AddVehicleRequest addRequest =
                new AddVehicleRequest(numberPlate, VehicleType.COMMERCIAL);
        vehicleManagementController.addVehicle(addRequest);
        DeleteVehicleRequest deleteRequest =
                new DeleteVehicleRequest(numberPlate);
        boolean expectedFound = true;
        when(vehicleManagementServiceMock.deleteVehicle(numberPlate)).thenReturn(expectedFound);
        // Exercise

        DeleteVehicleResponse actualResponse = vehicleManagementController.deleteVehicle(deleteRequest);

        // Verify

        assertNotNull(actualResponse, "Response is null.");
        assertEquals(expectedFound, actualResponse.isFound());

        verify(vehicleManagementServiceMock, times(1)).deleteVehicle(numberPlate);
        // Teardown - no teardown stage
    }

    @Test
    public void testDeleteNotPresentVehicle(){
        // Setup
        String numberPlate = "ABC123";
        DeleteVehicleRequest deleteRequest =
                new DeleteVehicleRequest(numberPlate);
        boolean expectedFound = false;
        when(vehicleManagementServiceMock.deleteVehicle(numberPlate)).thenReturn(expectedFound);

        // Exercise
        DeleteVehicleResponse actualResponse = vehicleManagementController.deleteVehicle(deleteRequest);

        // Verify

        assertNotNull(actualResponse, "Response is null.");
        assertEquals(expectedFound, actualResponse.isFound());

        verify(vehicleManagementServiceMock, times(1)).deleteVehicle(numberPlate);
        // Teardown - no teardown stage
    }

    @Test
    public void testGetPresentVehicleByNumberPlate(){
        //Setup
        String numberPlate = "ABC 123";
        Vehicle returnedVehicle = new Vehicle(numberPlate, VehicleType.FAMILY, 12,12);
        GetVehicleByNumberPlateResponse expectedResponse = mapper.map(returnedVehicle, GetVehicleByNumberPlateResponse.class);
        when(vehicleManagementServiceMock.getVehicleByNumberPlate(numberPlate)).thenReturn(returnedVehicle);

        //Exercise
        GetVehicleByNumberPlateResponse response = vehicleManagementController.getVehicleByNumberPlate(numberPlate);

        //Verify
        assertTrue(DeepEquals.deepEquals(response, expectedResponse));
        verify(vehicleManagementServiceMock, times(1)).getVehicleByNumberPlate(numberPlate);

        //Teardown - no teardown stage

    }

    @Test
    public void testGetNotPresentVehicleByNumberPlate(){
        //Setup
        String numberPlate = "ABC 123";
        when(vehicleManagementServiceMock.getVehicleByNumberPlate(numberPlate)).thenReturn(null);
        boolean caughtException = false;

        //Exercise
        try{
            GetVehicleByNumberPlateResponse response = vehicleManagementController.getVehicleByNumberPlate(numberPlate);
        }catch(VehicleNotFoundException notFoundException){
            caughtException = true;
        }

        //Verify
        assertTrue(caughtException);
        verify(vehicleManagementServiceMock, times(1)).getVehicleByNumberPlate(numberPlate);

        //Teardown - no teardown stage
    }

    @Test
    public void testGetOneVehicleByCapacity(){
        // Setup
        int capacity = 6;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        returnedVehicles.add(new Vehicle("ABC 123", VehicleType.FAMILY,
                120, capacity));
        GetVehicleResponse expectedResponse = new GetVehicleResponse(returnedVehicles);
        when(vehicleManagementServiceMock.
                getVehicleByCapacity(capacity, requestType)).thenReturn(returnedVehicles);

        // Exercise
        GetVehicleResponse response = vehicleManagementController.
                getVehicleByCapacity(capacity, requestType);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByCapacity(capacity, requestType);

        // Teardown - no teardown stage
    }

    @Test
    public void testGetFiveVehiclesByCapacity(){
        // Setup
        int capacity = 4;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        returnedVehicles.add(new Vehicle("ABC 123", VehicleType.FAMILY,
                125, capacity));
        returnedVehicles.add(new Vehicle("ABC 124", VehicleType.MOTORCYCLE,
                100, capacity));
        returnedVehicles.add(new Vehicle("ABC 125", VehicleType.COMMERCIAL,
                120, capacity));
        returnedVehicles.add(new Vehicle("ABC 126", VehicleType.COMMERCIAL,
                190, capacity));
        returnedVehicles.add(new Vehicle("AMC 123", VehicleType.FAMILY,
                90, capacity));
        GetVehicleResponse expectedResponse = new GetVehicleResponse(returnedVehicles);
        when(vehicleManagementServiceMock.
                getVehicleByCapacity(capacity, requestType)).thenReturn(returnedVehicles);

        // Exercise
        GetVehicleResponse response = vehicleManagementController.
                getVehicleByCapacity(capacity, requestType);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByCapacity(capacity, requestType);

        // Teardown - no teardown stage
    }

    @Test
    public void testGetZeroVehiclesByCapacity(){
        // Setup
        int capacity = 4;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        when(vehicleManagementServiceMock.
                getVehicleByCapacity(capacity, requestType)).thenReturn(returnedVehicles);
        boolean exceptionThrown = false;

        // Exercise
        try{
            GetVehicleResponse response = vehicleManagementController.
                    getVehicleByCapacity(capacity, requestType);
        }catch(VehicleNotFoundException notFoundException){
            exceptionThrown=true;
        }


        // Verify
        assertTrue(exceptionThrown);
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByCapacity(capacity, requestType);

        // Teardown - no teardown stage
    }

    @Test
    public void testGetOneVehicleByPrice(){
        // Setup
        double price = 80.0;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        returnedVehicles.add(new Vehicle("ABC 123", VehicleType.FAMILY,
                price, 8));
        GetVehicleResponse expectedResponse = new GetVehicleResponse(returnedVehicles);
        when(vehicleManagementServiceMock.
                getVehicleByPrice(price, requestType)).thenReturn(returnedVehicles);

        // Exercise
        GetVehicleResponse response = vehicleManagementController.
                getVehicleByPrice(price, requestType);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByPrice(price, requestType);

        // Teardown - no teardown stage
    }

    @Test
    public void testGetFiveVehiclesByPrice(){
        // Setup
        double price = 100.0;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        returnedVehicles.add(new Vehicle("ABC 123", VehicleType.FAMILY,
                price, 12));
        returnedVehicles.add(new Vehicle("ABC 124", VehicleType.MOTORCYCLE,
                price, 7));
        returnedVehicles.add(new Vehicle("ABC 125", VehicleType.COMMERCIAL,
                price, 9));
        returnedVehicles.add(new Vehicle("ABC 126", VehicleType.COMMERCIAL,
                price, 10));
        returnedVehicles.add(new Vehicle("AMC 123", VehicleType.FAMILY,
                price, 10));
        GetVehicleResponse expectedResponse = new GetVehicleResponse(returnedVehicles);
        when(vehicleManagementServiceMock.
                getVehicleByPrice(price, requestType)).thenReturn(returnedVehicles);

        // Exercise
        GetVehicleResponse response = vehicleManagementController.
                getVehicleByPrice(price, requestType);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByPrice(price, requestType);

        // Teardown - no teardown stage
    }

    @Test
    public void testGetZeroVehiclesByPrice(){
        // Setup
        double price =50.60;
        RequestType requestType = RequestType.EQUALS;
        List<Vehicle> returnedVehicles = new ArrayList<>();
        when(vehicleManagementServiceMock.
                getVehicleByPrice(price, requestType)).thenReturn(returnedVehicles);
        boolean exceptionThrown = false;

        // Exercise
        try{
            GetVehicleResponse response = vehicleManagementController.
                    getVehicleByPrice(price, requestType);
        }catch(VehicleNotFoundException notFoundException){
            exceptionThrown=true;
        }


        // Verify
        assertTrue(exceptionThrown);
        verify(vehicleManagementServiceMock, times(1)).
                getVehicleByPrice(price, requestType);

        // Teardown - no teardown stage
    }
}
