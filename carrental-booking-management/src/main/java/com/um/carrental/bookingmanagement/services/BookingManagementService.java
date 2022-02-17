package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.data.repositories.BookingRepository;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.exceptions.InvalidBookingRequestException;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingManagementService {
    @Autowired
    BookingRepository repository;

    @Autowired
    ModelMapper mapper;

    public AddBookingResponse addBooking(AddBookingRequest request){
        BookingScheduler bookingScheduler = BookingScheduler.getInstance();
        if(!bookingScheduler.dateTimeValid(request.getStartTime(), request.getHours()))
            throw new InvalidBookingRequestException();

        BookingEntity bookingEntity = mapper.map(request, BookingEntity.class);
        if(!bookingScheduler.noBookingOverlap(request.getStartTime(),
                request.getHours(), repository.findAll(), request.getNumberPlate())){
            bookingEntity.setStatus(BookingStatus.REJECTED);
        }else bookingEntity.setStatus(BookingStatus.ACCEPTED);

        bookingEntity.setBookingID(UUID.randomUUID().toString());

        repository.save(bookingEntity);

        return new AddBookingResponse(bookingEntity.getBookingID(), bookingEntity.getStatus());
    }

    public Booking getBooking(String bookingID){
        return null;
    }
}
