package com.um.carrental.customer_management.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason="This customer does not exist")
public class CustomerException extends NoSuchElementException {

    public CustomerException(){
        super();
    }
}
