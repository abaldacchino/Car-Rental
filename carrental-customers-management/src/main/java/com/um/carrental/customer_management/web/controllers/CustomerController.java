package com.um.carrental.customer_management.web.controllers;

import com.um.carrental.customer_management.services.AddCustomerService;
import com.um.carrental.customer_management.services.models.AddCustomerSubmission;
import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;
@RestController
public class CustomerController {

    // exposing one end point
    // HTTP Method is POST
    // used to create a customer - customer submission
    // expect to receive a request from AddCustomerRequest, X-Id (non-standard header) -> not needed
    // Give back a response -> SubmitCustomerResponse, 201

    // new instance of AddCustomerService
    @Autowired
    AddCustomerService addCustomerService;

    @Autowired
    ModelMapper mapper;

    @PostMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitCustomerResponse submit(@RequestHeader(name="X-Customer-Id") String customerId,  @RequestBody AddCustomerRequest request){
        // String customer = UUID.randomUUID().toString();
        // Call service layer
        // submitOrder(xxx) --> info that contains ID

        AddCustomerSubmission customerSubmission = mapper.map(request, AddCustomerSubmission.class);
        customerSubmission.setCustomerId(customerId);

        // AddCustomerSubmission customerSubmission = new AddCustomerSubmission();
        return new SubmitCustomerResponse(customerId);
        // return null;
    }

}
