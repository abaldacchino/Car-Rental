package com.um.carrental.customer_management.data.repo;

import com.um.carrental.customer_management.data.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddCustomerRepository extends JpaRepository<CustomerEntity, String> {


}
