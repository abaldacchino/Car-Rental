package com.um.carrental.bookingmanagement.data.entities;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class BookingEntity {
    @Id
    long bookingID;

    String numberPlate;
    
    String customerID;

    LocalDateTime startTime;

    int hours;
}
