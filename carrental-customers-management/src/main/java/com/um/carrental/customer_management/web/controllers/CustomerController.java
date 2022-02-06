package com.um.carrental.customer_management.web.controllers;

import com.um.carrental.customer_management.web.requests.AddCustomerRequest;
import com.um.carrental.customer_management.web.responses.SubmitCustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public class CustomerController {

    // exposing one end point
    // HTTP Method is POST
    // used to create a customer - customer submission
    // expect to receive a request from AddCustomerRequest, X-Id (non-standard header) -> not needed
    // Give back a response -> SubmitCustomerResponse, 201
    @PostMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitCustomerResponse submit(@RequestBody AddCustomerRequest request){
        String customer = UUID.randomUUID().toString();

        return new SubmitCustomerResponse(customer);
        // return null;
    }

}
