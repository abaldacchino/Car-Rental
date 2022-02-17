package com.um.carrental.customer_management.web.controllers;

import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

@RestController
public class CustomerController {

    // exposing one end point
    // HTTP Method is POST
    // used to create a customer - customer submission
    // expect to receive a request from AddCustomerRequest, X-Id (non-standard header)
    // Give back a response -> SubmitCustomerResponse, 201

    // new instance of AddCustomerService
    @Autowired
    AddCustomerService addCustomerService;

    @Autowired
    ModelMapper mapper;

    @PostMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitCustomerResponse submit(@RequestHeader(name="X-Customer-Id") String customerId,  @RequestBody AddCustomerRequest request){
        // Call service layer
        // submitOrder(xxx) --> info that contains ID

        CustomerSubmission customerSubmission = mapper.map(request, CustomerSubmission.class);
        customerSubmission.setCustomerId(customerId);
        Customer customer = addCustomerService.addCustomer(customerSubmission);
        customerId = customer.getCustomerId();

        return new SubmitCustomerResponse(customerId);
    }

    // @GetMapping()
    // @ResponseStatus()
}
