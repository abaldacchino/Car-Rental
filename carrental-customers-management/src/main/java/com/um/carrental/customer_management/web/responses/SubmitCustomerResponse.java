package com.um.carrental.customer_management.responses;

public class SubmitCustomerResponse {
    private String Id; // ID of the customer

    public SubmitCustomerResponse(String Id){
        this.Id = Id;
    }

    public SubmitCustomerResponse(){

    }

    public String getId(){
        return Id;
    }

    public void setId(String Id){
        this.Id = Id;
    }

}
