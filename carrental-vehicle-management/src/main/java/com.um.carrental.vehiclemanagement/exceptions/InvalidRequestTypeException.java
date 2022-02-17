package com.um.carrental.vehiclemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request type is invalid")
public class InvalidRequestTypeException extends RuntimeException{
}
