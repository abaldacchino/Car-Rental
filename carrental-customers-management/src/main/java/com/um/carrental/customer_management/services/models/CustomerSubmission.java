package com.um.carrental.customer_management.services.models;

import java.util.List;

public class CustomerSubmission {

    private List<CustomerDetails> customerDetails;
     private String customerId;

    public CustomerSubmission(List<CustomerDetails> customerDetails, String customerId){
        this.customerDetails = customerDetails;
         this.customerId = customerId;
    }

    public CustomerSubmission(){

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
