package com.um.carrental.customer_management.services;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.customer_management.data.entities.CustomerEntity;
import com.um.carrental.customer_management.data.repo.AddCustomerRepository;
import com.um.carrental.customer_management.services.models.Customer;
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
import java.util.Optional;
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

    @MockBean
    AddCustomerRepository repository;

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

    @Test
    public void testDeleteNonExistingCustomer(){
        // Setup
        String customerId = UUID.randomUUID().toString();
        boolean expectedFound = false;
        when(customerServiceMock.deleteCustomer(customerId)).thenReturn(expectedFound);
        // Exercise
        boolean actualFound = customerServiceMock.deleteCustomer(customerId);
        // Verify
        assertEquals(expectedFound, actualFound);
        verify(repository, times(0)).existsById(any(String.class));
        verify(repository, times(0)).delete(any(CustomerEntity.class));
    }


    @Test
    public void testGetValidCustomer(){

        // Setup
        List<com.um.carrental.customer_management.data.entities.CustomerDetails> entityCustomerDetails =
                List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("andrew borg", 73));

        String customerId = UUID.randomUUID().toString();
        Optional<CustomerEntity> repositoryCustomerEntity = Optional.of(new CustomerEntity(entityCustomerDetails, customerId));
        List<com.um.carrental.customer_management.services.models.CustomerDetails> customerDetails =
                List.of(new com.um.carrental.customer_management.services.models.CustomerDetails("andrew borg", 73));
        Customer expectedCustomer = new Customer(customerDetails, customerId);
        when(repository.findById(customerId)).thenReturn(repositoryCustomerEntity);

        // Exercise
        Customer actualCustomer = customerServiceMock.getCustomer(customerId);

        // Verify

        DeepEquals.deepEquals(actualCustomer, expectedCustomer);
    }

    @Test
    public void testGetInvalidCustomer(){

    }

    @Test
    public void testGetValidCustomerByName(){

    }

    @Test
    public void testGetInvalidCustomerByName(){

    }
}
