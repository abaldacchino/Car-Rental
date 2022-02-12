package com.um.carrental.vehiclemanagement.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.vehiclemanagement.enums.VehicleType;
import com.um.carrental.vehiclemanagement.exceptions.VehicleNotFoundException;
import com.um.carrental.vehiclemanagement.services.FamilyCar;
import com.um.carrental.vehiclemanagement.services.Vehicle;
import com.um.carrental.vehiclemanagement.services.VehicleManagementService;
import com.um.carrental.vehiclemanagement.services.VehicleSubmission;
import com.um.carrental.vehiclemanagement.web.controllers.VehicleManagementController;
import com.um.carrental.vehiclemanagement.web.requests.AddVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.DeleteVehicleRequest;
import com.um.carrental.vehiclemanagement.web.requests.GetVehicleByIdRequest;
import com.um.carrental.vehiclemanagement.web.responses.AddVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.DeleteVehicleResponse;
import com.um.carrental.vehiclemanagement.web.responses.GetVehicleByIdResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

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
    public void testGetPresentVehicleById(){
        //Setup
        String numberPlate = "ABC 123";
        GetVehicleByIdRequest request = new GetVehicleByIdRequest(numberPlate);
        Vehicle returnedVehicle = new FamilyCar(numberPlate);
        GetVehicleByIdResponse expectedResponse = mapper.map(returnedVehicle, GetVehicleByIdResponse.class);
        when(vehicleManagementServiceMock.getVehicleById(numberPlate)).thenReturn(returnedVehicle);

        //Exercise
        GetVehicleByIdResponse response = vehicleManagementController.getVehicleById(request);

        //Verify
        assertTrue(DeepEquals.deepEquals(response, expectedResponse));
        verify(vehicleManagementServiceMock, times(1)).getVehicleById(numberPlate);

        //Teardown - no teardown stage

    }

    @Test
    public void testGetNotPresentVehicleById(){
        //Setup
        String numberPlate = "ABC 123";
        GetVehicleByIdRequest request = new GetVehicleByIdRequest(numberPlate);
        when(vehicleManagementServiceMock.getVehicleById(numberPlate)).thenReturn(null);
        boolean caughtException = false;

        //Exercise
        try{
            GetVehicleByIdResponse response = vehicleManagementController.getVehicleById(request);
        }catch(VehicleNotFoundException notFoundException){
            caughtException = true;
        }

        //Verify
        assertTrue(caughtException);
        verify(vehicleManagementServiceMock, times(1)).getVehicleById(numberPlate);

        //Teardown - no teardown stage

    }
}
