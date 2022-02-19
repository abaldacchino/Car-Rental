package com.um.carrental.customer_management.web.requests;


import java.util.List;

public class UpdateCustomerRequest {

    String customerId;
    List<CustomerDetails> customerDetails;

    public UpdateCustomerRequest(String customerId, List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
        this.customerId = customerId;
    }

    public UpdateCustomerRequest(){

    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }

}
