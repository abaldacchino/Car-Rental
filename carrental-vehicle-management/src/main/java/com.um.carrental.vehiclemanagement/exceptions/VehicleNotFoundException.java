package com.um.carrental.vehiclemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Vehicle not found")
public class VehicleNotFoundException extends RuntimeException{
}
