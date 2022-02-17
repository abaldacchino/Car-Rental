package com.um.carrental.customer_management.web;


import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.controllers.CustomerController;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.requests.CustomerDetails;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
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

                // Arrange

                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                when(customerServiceMock.addCustomer(any(CustomerSubmission.class))).thenReturn(customerId);

                // Act
                SubmitCustomerResponse response = customerController.submit(customerId, request);

                // Assert
                assertNotNull(response, "Response is null");
                String receivedId = response.getId();
                assertNotNull(receivedId, "Customer Id is null");
                assertEquals(customerId, receivedId);
        }
}
