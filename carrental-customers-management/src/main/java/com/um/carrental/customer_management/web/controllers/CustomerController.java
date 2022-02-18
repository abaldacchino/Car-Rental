package com.um.carrental.customer_management.controllers;

import com.um.carrental.customer_management.requests.AddCustomerRequest;
import com.um.carrental.customer_management.responses.GetCustomerResponse;
import com.um.carrental.customer_management.responses.SubmitCustomerResponse;
import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.services.models.CustomerDetails;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@RestController
public class CustomerController {

    // exposing one end point
    // HTTP Method -> POST
    // used to create a customer - customer submission
    // Expect -> AddCustomerRequest
    // Response -> SubmitCustomerResponse, 201

    // new instance of AddCustomerService
    @Autowired
    AddCustomerService addCustomerService;

    @Autowired
    ModelMapper mapper;

    @PostMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitCustomerResponse submit(@RequestHeader(name="X-Customer-Id") String customerId, @RequestBody AddCustomerRequest request){
        // Call service layer
        // submitOrder(xxx) --> info that contains ID

        CustomerSubmission customerSubmission = mapper.map(request, CustomerSubmission.class);
        customerSubmission.setCustomerId(customerId);
        // Customer customer = addCustomerService.addCustomer(customerSubmission);
        customerId = addCustomerService.addCustomer(customerSubmission); // customer.getCustomerId();

        return new SubmitCustomerResponse(customerId);
    }

    // HTTP -> GET
    // Get customer details by Id
    // Expect -> getCustomerResponse
    // Response -> OK, 200
    @GetMapping(value = "customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetCustomerResponse getById(@PathVariable String customerId) {

        Customer customer = addCustomerService.getCustomer(customerId);
        List<CustomerDetails> customerDetails = customer.getCustomerDetails();
        GetCustomerResponse getCustomerResponse = new GetCustomerResponse(customerId, customerDetails);

        System.out.println(getCustomerResponse.getCustomerDetails());

        return getCustomerResponse;
    }

}
