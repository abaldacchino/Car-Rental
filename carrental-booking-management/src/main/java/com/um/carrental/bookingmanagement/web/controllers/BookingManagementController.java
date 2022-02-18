package com.um.carrental.bookingmanagement.web.controllers;

import com.um.carrental.bookingmanagement.enums.BookingStatusQuery;
import com.um.carrental.bookingmanagement.services.BookingManagementService;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import com.um.carrental.bookingmanagement.web.responses.GetBookingListResponse;
import com.um.carrental.bookingmanagement.web.responses.GetBookingResponse;
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

    // Get a booking
    // Method --> GET
    // Request --> String bookingID
    // Response --> 200 (OK), GetBookingResponse
    @GetMapping(value = "booking/{bookingID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetBookingResponse getBooking(@RequestParam(value="bookingID") String bookingID){
        return mapper.map(bookingManagementService.getBooking(bookingID), GetBookingResponse.class);
    }

    // Get a list of bookings
    // Method --> POST
    // Request --> BookingStatusQuery
    // Response --> 200 (OK), GetBookingListResponse
    @GetMapping(value = "booking/{bookingStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GetBookingListResponse getBookingList(
            @RequestParam(value="bookingStatus")BookingStatusQuery bookingStatus){
        return new GetBookingListResponse(bookingManagementService.getBookingList(bookingStatus));
    }
}
