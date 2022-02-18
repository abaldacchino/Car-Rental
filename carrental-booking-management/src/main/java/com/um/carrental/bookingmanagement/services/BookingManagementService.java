package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.data.repositories.BookingRepository;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.enums.BookingStatusQuery;
import com.um.carrental.bookingmanagement.exceptions.BookingNotFoundException;
import com.um.carrental.bookingmanagement.exceptions.InvalidBookingRequestException;
import com.um.carrental.bookingmanagement.messaging.MessagingServiceInterface;
import com.um.carrental.bookingmanagement.messaging.MessagingServiceProxy;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class BookingManagementService {
    @Autowired
    BookingRepository repository;

    @Autowired
    ModelMapper mapper;

    MessagingServiceInterface messaging = new MessagingServiceProxy();

    public AddBookingResponse addBooking(AddBookingRequest request){
        BookingScheduler bookingScheduler = BookingScheduler.getInstance();
        // Bad Request (invalid time/date)
        if(!bookingScheduler.dateTimeValid(request.getStartTime(), request.getHours()))
            throw new InvalidBookingRequestException();

        BookingEntity bookingEntity = mapper.map(request, BookingEntity.class);

        double price = messaging.getVehiclePrice(request.getNumberPlate());
        // Rejected Request (customer/vehicle do not exist, or there is booking overlap)
        if(!messaging.customerExistsById(request.getCustomerID()) ||
            price==-1 ||
            !bookingScheduler.noBookingOverlap(request.getStartTime(),
                request.getHours(), repository.findAll(), request.getNumberPlate())){
            bookingEntity.setStatus(BookingStatus.REJECTED);
        }else{
            // Accepted request
            bookingEntity.setStatus(BookingStatus.ACCEPTED);
            bookingEntity.setTotalPrice(price* request.getHours());
        }

        bookingEntity.setBookingID(UUID.randomUUID().toString());
        repository.save(bookingEntity);

        return new AddBookingResponse(bookingEntity.getBookingID(), bookingEntity.getStatus());
    }

    public Booking getBooking(String bookingID){
        if(repository.existsById(bookingID)){
            Booking booking = mapper.map(repository.getById(bookingID), Booking.class);
            return booking;
        }
        throw new BookingNotFoundException();
    }

    public List<Booking> getBookingList(BookingStatusQuery bookingStatus){
        List<BookingEntity> bookingEntityList = repository.findAll();
        Iterator<BookingEntity> bookingEntityIterator = bookingEntityList.listIterator();
        List<Booking> bookingList = new ArrayList<>();

        while(bookingEntityIterator.hasNext()){
            BookingEntity bookingEntity = bookingEntityIterator.next();
            switch(bookingStatus){
                case ANY:
                    bookingList.add(mapper.map(bookingEntity, Booking.class));
                    break;
                case ACCEPTED:
                    if(bookingEntity.getStatus()==BookingStatus.ACCEPTED)
                        bookingList.add(mapper.map(bookingEntity, Booking.class));
                    break;
                case REJECTED:
                    if(bookingEntity.getStatus()==BookingStatus.REJECTED)
                        bookingList.add(mapper.map(bookingEntity, Booking.class));
                    break;
                case CANCELLED:
                    if(bookingEntity.getStatus()==BookingStatus.CANCELLED)
                        bookingList.add(mapper.map(bookingEntity, Booking.class));
                    break;
            }
        }
        return bookingList;
    }

    public void cancelBooking(String bookingID){

    }

    // Setter for messaging service -- can be used to modify behaviour of calling
    // different microservices ... mostly used for testing
    public void setMessaging(MessagingServiceInterface messaging) {
        this.messaging = messaging;
    }
}
