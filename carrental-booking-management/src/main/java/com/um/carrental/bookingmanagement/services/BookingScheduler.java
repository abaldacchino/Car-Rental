package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class BookingScheduler {
    private static volatile BookingScheduler instance = null;

    private BookingScheduler() {
    }

    // Lazy instantiation
    public static BookingScheduler getInstance() {
        if (instance == null) {
            instance = new BookingScheduler();
        }
        return instance;
    }

    boolean noBookingOverlap(LocalDateTime startTime, int hours, List<BookingEntity> bookings){

        LocalDateTime endTime = startTime.plusHours(hours);

        Iterator<BookingEntity> iterator = bookings.listIterator();
        while(iterator.hasNext()){
            BookingEntity booking = iterator.next();
            //Only consider accepted bookings
            if(booking.getStatus() == BookingStatus.ACCEPTED){
                LocalDateTime bookingStartTime = booking.getStartTime();
                LocalDateTime bookingEndTime = bookingStartTime.plusHours(booking.getHours());

                // startTime < endTime (by assumption)
                // so we just need to ensure that endTime < bookingStartTime
                // or startTime < bookingEndTime
                if(!endTime.isBefore(bookingStartTime) &&
                    !startTime.isAfter(bookingEndTime)) return false;
            }
        }
        return true;
    }

    boolean dateTimeValid(LocalDateTime dateTime, int hours){
        return dateTimeInFuture(dateTime) &&
                timeDuringWorkingHours(dateTime, hours) &&
                validHours(hours);
    }

    boolean validHours(int hours){
        return hours>0;
    }

    boolean dateTimeInFuture(LocalDateTime dateTime){
        return dateTime.isAfter(LocalDateTime.now());
    }

    boolean timeDuringWorkingHours(LocalDateTime dateTime, int hours){
        int startHour = dateTime.getHour();
        if(dateTime.getMinute() >0)startHour++;

        if(startHour <8 || startHour>18)return false;
        int endHour = (startHour+hours)%24;
        if(endHour <8 || endHour>18)return false;

        return true;
    }


}
