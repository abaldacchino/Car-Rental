package com.um.carrental.customer_management.web.requests;

import java.util.List;
public class AddCustomerRequest {

    // expecting to receive a list of customer
    private List<CustomerDetails> customerDetails;

    public AddCustomerRequest(List<CustomerDetails> customerDetails){ this.customerDetails = customerDetails;}

    public AddCustomerRequest() {

    }

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }

}
