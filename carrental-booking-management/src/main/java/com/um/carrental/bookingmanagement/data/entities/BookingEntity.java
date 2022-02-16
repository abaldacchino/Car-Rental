package com.um.carrental.bookingmanagement.data.entities;

import com.um.carrental.bookingmanagement.enums.BookingStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class BookingEntity {
    @Id
    String bookingID;

    String numberPlate;

    String customerID;

    LocalDateTime startTime;

    int hours;

    @Enumerated(EnumType.STRING)
    BookingStatus status;
}
