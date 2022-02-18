package com.um.carrental.customer_management.web.responses;

public class DeleteCustomerResponse {

    boolean foundCustomer;

    public DeleteCustomerResponse(){

    }

    public DeleteCustomerResponse(boolean foundCustomer){
        this.foundCustomer = foundCustomer;
    }

    public boolean getCustomerBoolean(){
        return foundCustomer;
    }

    public void setFoundCustomer(boolean foundCustomer){
        this.foundCustomer = foundCustomer;
    }
}
