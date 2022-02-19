package com.um.carrental.customer_management.data.entities;

import com.um.carrental.customer_management.services.models.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerEntity {

    @ElementCollection
    @CollectionTable(name="CUSTOMER_DETAILS", joinColumns = @JoinColumn(name="CUSTOMER_ID"))
    private List<CustomerDetails> customerDetails;

    @Id
    private String customerId;

    public CustomerEntity(List<CustomerDetails> customerDetails, String customerId){
        this.customerDetails = customerDetails;
        this.customerId = customerId;
    }
    public CustomerEntity(){
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails){
        this.customerDetails = customerDetails;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }
    public List<CustomerDetails> getCustomerDetails(){ return customerDetails;}

    public String getCustomerId(){
        return customerId;
    }

    public String getCustomerName(int position){
        String name  = customerDetails.get(position).getName();
        return name;
    }

}
