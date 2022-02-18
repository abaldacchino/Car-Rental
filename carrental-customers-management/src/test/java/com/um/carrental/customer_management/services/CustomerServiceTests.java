package com.um.carrental.customer_management.services;

import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.controllers.CustomerController;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.requests.CustomerDetails;
import com.um.carrental.customer_management.web.responses.DeleteCustomerResponse;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTests {

    @Autowired
    CustomerController customerController;

    @Autowired
    ModelMapper mapper = new ModelMapper();

    @MockBean
    AddCustomerService customerServiceMock;

    @Test
    public void testAddCustomer(){
        // Setup
        AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
        String customerId = UUID.randomUUID().toString();
        when(customerServiceMock.addCustomer(any(CustomerSubmission.class))).thenReturn(customerId);
        // Exercise
        SubmitCustomerResponse actualResponse = customerController.submit(customerId, request);
        // Verify
        assertNotNull(actualResponse, "Response is null");
        assertEquals(customerId, actualResponse.getId());
    }

    @Test
    public void testDeleteCustomer(){
        // Setup
        String customerId = UUID.randomUUID().toString();
        AddCustomerRequest customerRequest = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
        customerController.submit(customerId, customerRequest);
        boolean expectedFound = true;
        when(customerServiceMock.deleteCustomer(customerId)).thenReturn(expectedFound);
        // Exercise
        DeleteCustomerResponse actualResponse = customerController.deleteCustomer(customerId);
        // Verify
        assertNotNull(actualResponse, "Response is null");
        assertEquals(expectedFound, actualResponse.getCustomerBoolean());
        verify(customerServiceMock, times(1)).deleteCustomer(customerId);

    }
//    @Test
//    public void testGetValidCustomer(){
//
//    }
    // public void testDeleteNonExistingCustomer()
}
