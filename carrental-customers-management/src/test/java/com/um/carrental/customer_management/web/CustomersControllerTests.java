package com.um.carrental.customer_management.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.customer_management.data.entities.CustomerEntity;
import com.um.carrental.customer_management.data.repo.AddCustomerRepository;
import com.um.carrental.customer_management.exceptions.CustomerException;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.requests.CustomerDetails;
import com.um.carrental.customer_management.web.requests.UpdateCustomerRequest;
import com.um.carrental.customer_management.web.responses.DeleteCustomerResponse;
import com.um.carrental.customer_management.web.responses.GetCustomerResponse;
import com.um.carrental.customer_management.web.responses.GetCustomerResponseByName;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.controllers.CustomerController;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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

        @MockBean
        AddCustomerRepository repository;

        @Test
        public void testSubmitValidCustomer(){

                // Arrange - Setup

                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                when(customerServiceMock.addCustomer(any(CustomerSubmission.class))).thenReturn(true);

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
        public void testGetNonExistentCustomer(){
                // Setup
                String customerId = "000123M";
                when(repository.existsById(customerId)).thenReturn(false);

                //Exercise & Verify
                assertThrows(CustomerException.class, new Executable() {
                        @Override
                        public void execute() throws Throwable {
                                GetCustomerResponse response = customerController.getById(customerId);
                        }
                });
                // Verify
                verify(repository, times(0)).getById(customerId);
        }
        @Test
        public void testGetValidCustomerByName(){
                // Setup
                String customerId = UUID.randomUUID().toString();
                String customerName = "andrew borg";
                List<com.um.carrental.customer_management.services.models.CustomerDetails> customerDetails = List.of(new com.um.carrental.customer_management.services.models.CustomerDetails("andrew borg", 73));
                Customer customer = new Customer(customerDetails, customerId);
                List<Customer> customers = new ArrayList<>(); // List.of(new Customer(customerDetails, customerId));
                customers.add(customer);
                GetCustomerResponseByName expectedCustomerResponse = new GetCustomerResponseByName(customers);
                when(customerServiceMock.getCustomerByName(customerName)).thenReturn(customers);
                // Exercise
                GetCustomerResponseByName actualCustomerResponse = customerController.getByName(customerName);
                // Verify
                assertNotNull(actualCustomerResponse);
                assertTrue(DeepEquals.deepEquals(actualCustomerResponse, expectedCustomerResponse));
                // No Teardown needed
        }

        @Test
        public void testGetInvalidCustomerByName(){
                // Setup
                String customerName = "andrew borg";
                when(repository.existsById(customerName)).thenReturn(false);
                //Exercise & Verify
                assertThrows(CustomerException.class, new Executable() {
                        @Override
                        public void execute() throws Throwable {
                                GetCustomerResponseByName response = customerController.getByName(customerName);
                        }
                });
                // Verify
                verify(repository, times(0)).getById(customerName);
        }

        @Test
        public void testDeletionOfValidCustomer(){
             // Setup
                String customerId = "000111M";
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                CustomerEntity customerEntity = new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("andrew borg", 73)), customerId);
                repository.save(customerEntity);
                when(customerServiceMock.deleteCustomer(customerId)).thenReturn(true);
                // Exercise
                DeleteCustomerResponse actualResponse = customerController.deleteCustomer(customerId);
             // Verify
                assertNotNull(actualResponse, "Response is null");
                assertTrue(actualResponse.getCustomerBoolean());
                verify(customerServiceMock, times(1)).deleteCustomer(customerId);
             // No Teardown
        }

        @Test
        public void testDeletionOfNonExistentCustomer(){
                // Setup
                String customerId = UUID.randomUUID().toString();
                AddCustomerRequest request = new AddCustomerRequest(List.of(new CustomerDetails("andrew borg", 73)));
                boolean expected = false;
                when(customerServiceMock.deleteCustomer(customerId)).thenReturn(expected);
                // Exercise
                DeleteCustomerResponse actualResponse = customerController.deleteCustomer(customerId);
                // Verify
                assertNotNull(actualResponse, "Response is null");
                assertEquals(expected, actualResponse.getCustomerBoolean());

                verify(customerServiceMock, times(1)).deleteCustomer(customerId);
                // No Teardown
        }

        @Test
        public void testUpdateExistingCustomer(){
                // Setup
                UpdateCustomerRequest request = new UpdateCustomerRequest("00123M", List.of(new CustomerDetails("andrew borg", 73)));
                when(customerServiceMock.updateCustomer(request)).thenReturn(true);

                // Exercise
                GetCustomerResponse response = customerController.updateCustomer(request);

                // Verify
                verify(customerServiceMock, times(1)).updateCustomer(request);
        }
        @Test
        public void testUpdateNonExistingCustomer(){
                // Setup
                UpdateCustomerRequest request = new UpdateCustomerRequest("00123M", List.of(new CustomerDetails("andrew borg", 73)));
                when(customerServiceMock.updateCustomer(request)).thenReturn(false);

                // Exercise
                assertThrows(CustomerException.class, new Executable() {
                        @Override
                        public void execute() throws Throwable {
                                GetCustomerResponse response = customerController.updateCustomer(request);
                        }
                });
                // Verify
                verify(customerServiceMock, times(1)).updateCustomer(request);

        }
}
