package com.um.carrental.customer_management.services;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.customer_management.data.entities.CustomerDetails;
import com.um.carrental.customer_management.data.entities.CustomerEntity;
import com.um.carrental.customer_management.data.repo.AddCustomerRepository;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.responses.DeleteCustomerResponse;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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
    ModelMapper mapper = new ModelMapper();

    @Autowired
    AddCustomerService addCustomerService;

    @MockBean
    AddCustomerRepository repository;

    @Test
    public void testAddCustomer(){
        // Setup
        CustomerSubmission customerSubmission = new CustomerSubmission();
        customerSubmission.setCustomerId("000123M");
        customerSubmission.setCustomerDetails(List.of(new com.um.carrental.customer_management.services.models.CustomerDetails("test name", 73)));

        CustomerEntity expectedCustomerEntity = mapper.map(customerSubmission, CustomerEntity.class);
        CustomerEntity savedCustomerEntity = repository.save(expectedCustomerEntity);
        when(repository.save(expectedCustomerEntity)).thenReturn(savedCustomerEntity);

        // Exercise
        String Id = addCustomerService.addCustomer(customerSubmission);

        // Verify
        assertNotNull(customerSubmission, "Customer submission is null");
        assertNotNull(expectedCustomerEntity, "Expected customer entity is null");
        assertNotNull(savedCustomerEntity, "Saved entity is null");
        assertNotNull(Id, "Id is null");
        assertEquals(Id, customerSubmission.getCustomerId());

    }

    @Test
    public void testDeleteCustomer(){
        // Setup
        String customerId = UUID.randomUUID().toString();
        when(repository.existsById(customerId)).thenReturn(true);

        // Exercise
        boolean found = addCustomerService.deleteCustomer(customerId);
        // Verify
        assertTrue(found);
        verify(repository, times(1)).deleteById(customerId);
        verify(repository, times(1)).existsById(customerId);
    }

    @Test
    public void testDeleteNonExistingCustomer(){
        // Setup
        String customerId = UUID.randomUUID().toString();
        boolean expectedFound = false;
        when(addCustomerService.deleteCustomer(customerId)).thenReturn(expectedFound);
        // Exercise
        boolean actualFound = addCustomerService.deleteCustomer(customerId);
        // Verify
        assertEquals(expectedFound, actualFound);
        verify(repository, times(1)).existsById(any(String.class));
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
        Customer actualCustomer = addCustomerService.getCustomer(customerId);
        // Verify
        DeepEquals.deepEquals(actualCustomer, expectedCustomer);
    }

    @Test
    public void testGetInvalidCustomer(){
        // Setup
         String customerId = "andrew borg";
         when(repository.existsById(customerId)).thenReturn(false);
         //Exercise
        Customer response = addCustomerService.getCustomer(customerId);
        //Verify
        assertNull(response);
        verify(repository, times(1)).existsById(customerId);
    }

    @Test
    public void testGetValidCustomerByName(){
        // Setup
        List<CustomerEntity> customerEntityList = new ArrayList<>();
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("andrew borg", 73)), "00123M"));
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("andrew galea", 33)), "00124M"));
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("maria vella", 20)), "00125M"));
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("julia tabone", 54)), "00126M"));
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("john bugeja", 60)), "00127M"));
        customerEntityList.add(new CustomerEntity(List.of(new com.um.carrental.customer_management.data.entities.CustomerDetails("guza bugeja", 40)), "00128M"));
        when(repository.findAll()).thenReturn(customerEntityList);

        List<Customer> expectedResponse = new ArrayList<>();
        expectedResponse.add(new Customer(List.of(new com.um.carrental.customer_management.services.models.CustomerDetails("maria vella", 20)), "00125M"));
        // Exercise
        List<Customer> response = addCustomerService.getCustomerByName("maria vella");

        // Verify
        assertTrue(DeepEquals.deepEquals(response, expectedResponse));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetInvalidCustomerByName(){
        // Setup
        String customerName = "andrew borg";
        when(repository.existsById(customerName)).thenReturn(false);
        List<Customer> customers = new ArrayList<>();
        //Exercise
        customers = addCustomerService.getCustomerByName(customerName);
        //Verify
        assertTrue(customers.isEmpty());
        verify(repository, times(1)).findAll();
    }
}
