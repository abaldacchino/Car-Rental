package com.um.carrental.customer_management.services.models;

import java.util.List;

public class AddCustomerSubmission {

    private List<CustomerDetails> customerDetails;
    private String customerId;

    public AddCustomerSubmission(List<CustomerDetails> customerDetails, String customerId){
        this.customerDetails = customerDetails;
        this.customerId = customerId;
    }

    public AddCustomerSubmission(){

    }
    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }
    public List<CustomerDetails> getCustomerDetails(){
        return customerDetails;
    }

    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public String getCustomerId(){
        return customerId;
    }

}
