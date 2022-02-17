package com.um.carrental.customer_management.data.entities;

import com.um.carrental.customer_management.services.models.Customer;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerEntity {

    // the list customer details will be represented as a table, each field having a column
    // the table CUSTOMER_DETAILS will be joined to CUSTOMER_ENTITY through CUSTOMER_ID
    // create a new table CUSTOMER_DETAILS to store all items inside customerDetails,
    // it will join that table with the CUSTOMER_ENTITY table through CUSTOMER_ID

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

}
