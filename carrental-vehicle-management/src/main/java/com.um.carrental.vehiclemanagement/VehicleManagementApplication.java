package com.um.carrental.vehiclemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VehicleManagementApplication{
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VehicleManagementApplication.class, args);
    }
}
