package com.um.carrental.customer_management.services;

import com.um.carrental.customer_management.data.entities.CustomerEntity;
import com.um.carrental.customer_management.data.repo.AddCustomerRepository;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.services.models.Customer;
import org.hibernate.hql.spi.id.AbstractIdsBulkIdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class AddCustomerService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    AddCustomerRepository repository;

    public Customer addCustomer(CustomerSubmission customerSubmission){
        Customer customer = mapper.map(customerSubmission, Customer.class);

        // call repo layer to save data
        CustomerEntity customerEntity = mapper.map(customer, CustomerEntity.class);
        CustomerEntity savedEntity = repository.save(customerEntity);

        Customer savedCustomer = mapper.map(savedEntity, Customer.class);
       
        return savedCustomer;
    }
}
