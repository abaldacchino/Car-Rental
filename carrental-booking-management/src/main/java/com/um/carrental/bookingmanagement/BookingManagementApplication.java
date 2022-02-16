package com.um.carrental.bookingmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookingManagementApplication{
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BookingManagementApplication.class, args);
    }
}
