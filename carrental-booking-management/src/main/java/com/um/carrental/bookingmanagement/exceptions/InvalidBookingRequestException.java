package com.um.carrental.bookingmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request is invalid")
public class InvalidBookingRequestException extends RuntimeException{
}
