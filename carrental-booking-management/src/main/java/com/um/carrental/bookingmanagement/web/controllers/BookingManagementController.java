package com.um.carrental.bookingmanagement.web.controllers;

import com.um.carrental.bookingmanagement.services.BookingManagementService;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BookingManagementController {
    @Autowired
    BookingManagementService bookingManagementService;

    @Autowired
    ModelMapper mapper;

    // Create a booking
    // Method --> POST
    // Request --> AddBookingRequest
    // Response --> 201 (Created), AddBookingResponse
    @PostMapping(value = "booking", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AddBookingResponse addBooking(@RequestBody AddBookingRequest request){
        AddBookingResponse response = bookingManagementService.addBooking(request);
        return response;
    }
}
