package com.um.carrental.customer_management.web;


import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.requests.CustomerDetails;
import com.um.carrental.customer_management.web.responses.DeleteCustomerResponse;
import com.um.carrental.customer_management.web.responses.GetCustomerResponse;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.controllers.CustomerController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CustomersControllerTests{

        @Autowired
        CustomerController customerController;

        @MockBean
        AddCustomerService customerServiceMock;

        @Test
        public void testSubmitValidCustomer(){

                // Arrange - Setup

                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                when(customerServiceMock.addCustomer(any(CustomerSubmission.class))).thenReturn(customerId);

                // Act - Exercise
                SubmitCustomerResponse response = customerController.submit(customerId, request);

                // Assert - Verify
                assertNotNull(response, "Response is null");
                String receivedId = response.getId();
                assertNotNull(receivedId, "Customer Id is null");
                assertEquals(customerId, receivedId);

                // No teardown needed
        }

        @Test
        public void testGetValidCustomerDetailsById(){
                // Setup
                String customerId = UUID.randomUUID().toString();
                List<com.um.carrental.customer_management.services.models.CustomerDetails> customerDetails = List.of(new com.um.carrental.customer_management.services.models.CustomerDetails("andrew borg", 73));
                Customer customer = new Customer(customerDetails, customerId);
                GetCustomerResponse expectedCustomerResponse = new GetCustomerResponse(customerId, customerDetails);
                when(customerServiceMock.getCustomer(customerId)).thenReturn(customer);
                // Exercise
                GetCustomerResponse actualCustomerResponse = customerController.getById(customerId);
                // Verify
                assertTrue(DeepEquals.deepEquals(actualCustomerResponse, expectedCustomerResponse));
                verify(customerServiceMock, times(1)).getCustomer(customerId);
                // No Teardown needed
        }
        @Test
        public void testDeletionOfValidCustomer(){
             // Setup
                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                customerController.submit(customerId, request);
                boolean expectedFound = true;
                when(customerServiceMock.deleteCustomer(customerId)).thenReturn(expectedFound);
                // Exercise
                DeleteCustomerResponse actualResponse = customerController.deleteCustomer(customerId);
             // Verify
                assertNotNull(actualResponse, "Response is null");
                assertEquals(expectedFound, actualResponse.getCustomerBoolean());

                verify(customerServiceMock, times(1)).deleteCustomer(customerId);
             // No Teardown
        }

        @Test
        public void testDeletionOfNonExistentCustomer(){
                // Setup
                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                customerController.submit(customerId, request);
                boolean expectedFound = false;
                when(customerServiceMock.deleteCustomer(customerId)).thenReturn(expectedFound);
                // Exercise
                DeleteCustomerResponse actualResponse = customerController.deleteCustomer(customerId);
                // Verify
                assertNotNull(actualResponse, "Response is null");
                assertEquals(expectedFound, actualResponse.getCustomerBoolean());

                verify(customerServiceMock, times(1)).deleteCustomer(customerId);
                // No Teardown
        }

//        @Test
//        public void testGetNonExistentCustomer(){
//
//        }
}
