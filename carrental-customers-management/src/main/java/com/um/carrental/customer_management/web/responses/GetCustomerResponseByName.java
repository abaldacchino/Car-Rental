package com.um.carrental.customer_management.web.responses;

import com.um.carrental.customer_management.services.models.Customer;

import java.util.List;

public class GetCustomerResponseByName {

    List<Customer> customers;
    public GetCustomerResponseByName(List<Customer> customers){
        this.customers = customers;
    }

    public GetCustomerResponseByName(){

    }

    public List<Customer> getCustomers(){
        return customers;
    }

    public void setCustomers(List<Customer> customers){
        this.customers = customers;
    }
}
