package com.um.carrental.customer_management.web.responses;
import com.um.carrental.customer_management.web.requests.CustomerDetails;

import java.util.List;
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
