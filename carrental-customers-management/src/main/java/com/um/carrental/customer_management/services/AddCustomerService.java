package com.um.carrental.customer_management.services;

import com.um.carrental.customer_management.services.models.AddCustomerSubmission;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.services.models.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class AddCustomerService {

    @Autowired
    ModelMapper mapper;

    public Customer addCustomer(AddCustomerSubmission addCustomerSubmission){
        Customer customer = mapper.map(addCustomerSubmission, Customer.class);

        return customer;
    }




}
