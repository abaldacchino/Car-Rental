package com.um.carrental.customer_management.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Invalid credentials")
public class InvalidCustomerException extends RuntimeException{
    public InvalidCustomerException(){
        super();
    }
}
