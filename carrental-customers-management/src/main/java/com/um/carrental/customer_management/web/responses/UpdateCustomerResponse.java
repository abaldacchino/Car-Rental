package com.um.carrental.customer_management.web.responses;

import com.um.carrental.customer_management.services.models.CustomerDetails;

import java.util.List;

public class UpdateCustomerResponse {

    String customerId;
    List<CustomerDetails> customerDetails;

    public UpdateCustomerResponse(){

    }

    public UpdateCustomerResponse(String customerId, List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
        this.customerId = customerId;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public String getCustomerId() {
        return customerId;
    }
}
