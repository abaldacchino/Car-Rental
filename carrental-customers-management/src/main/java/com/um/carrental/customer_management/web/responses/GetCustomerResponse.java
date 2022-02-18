package com.um.carrental.customer_management.web.responses;

import com.um.carrental.customer_management.web.requests.CustomerDetails;

import java.util.List;

public class GetCustomerResponse {

    String customerId;
    List<CustomerDetails> customerDetails;

    public GetCustomerResponse(String customerId, List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
        this.customerId = customerId;
    }
    public GetCustomerResponse(){

    }
}
