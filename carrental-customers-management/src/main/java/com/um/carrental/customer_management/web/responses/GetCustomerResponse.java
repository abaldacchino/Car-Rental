package com.um.carrental.customer_management.web.responses;

import com.um.carrental.customer_management.services.models.CustomerDetails;

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

    public GetCustomerResponse(String customerId){
        this.customerId = customerId;
    }

    public GetCustomerResponse(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }

    public String getCustomerId(){
        return customerId;
    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }
}
