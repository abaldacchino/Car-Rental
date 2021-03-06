package com.um.carrental.customer_management.services;

import com.um.carrental.customer_management.data.entities.CustomerEntity;
import com.um.carrental.customer_management.data.repo.AddCustomerRepository;
import com.um.carrental.customer_management.services.models.CustomerSubmission;
import com.um.carrental.customer_management.services.models.Customer;
import com.um.carrental.customer_management.web.requests.UpdateCustomerRequest;
import org.hibernate.hql.spi.id.AbstractIdsBulkIdHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AddCustomerService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    AddCustomerRepository repository;

    public boolean addCustomer(CustomerSubmission customerSubmission){
        // call repo layer to save data
        CustomerEntity customerEntity = mapper.map(customerSubmission, CustomerEntity.class);

        if(!repository.existsById(customerEntity.getCustomerId())){
            repository.save(customerEntity);
            return true;
        }
        return false;
    }

    public Customer getCustomer(String customerId){

        if(repository.existsById(customerId))
        {
            Optional<CustomerEntity> customerEntity = repository.findById(customerId);

            Customer customer = mapper.map(customerEntity.get(), Customer.class);
            return customer;
        }
        return null;
    }

    public List<Customer> getCustomerByName(String name){

        List<CustomerEntity> customerEntityList = repository.findAll();

        Iterator<CustomerEntity> iterator = customerEntityList.listIterator();
        List<Customer> foundCustomers = new ArrayList<>();
        while(iterator.hasNext()){
            CustomerEntity customerEntity = iterator.next();
            if(customerEntity.getCustomerName(0).equals(name)){
                foundCustomers.add(mapper.map(customerEntity, Customer.class));
            }
        }
        return foundCustomers;
    }

    public boolean deleteCustomer(String customerId){
        if(!repository.existsById(customerId)){
            return false;
        }
        else {
            repository.deleteById(customerId);
        }
        return true;
    }

    public boolean updateCustomer(UpdateCustomerRequest request){
        if(repository.existsById(request.getCustomerId())){
            repository.save(mapper.map(request, CustomerEntity.class));
            return true;
        }
        return false;
    }

}
