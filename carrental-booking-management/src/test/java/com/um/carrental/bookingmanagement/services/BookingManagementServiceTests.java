package com.um.carrental.bookingmanagement.services;

import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.data.repositories.BookingRepository;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.helpers.MyAssertions;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class BookingManagementServiceTests {
    @Autowired
    BookingManagementService bookingManagementService;

    @MockBean
    BookingRepository repositoryMock;

    @Test
    public void addValidBooking(){
        // Setup

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of existing accepted bookings in system
        List<BookingEntity> bookingEntityList = new ArrayList<>();
        // bookingID, numberPlate, customerID, startTime, hours, status

        // Booking 1 from two days from now 14:00 until 9:00 (next day)
        bookingEntityList.add(new BookingEntity("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED));
        // Booking 2 from three days from now 10:00 until 12:00 (same day)
        bookingEntityList.add(new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED));
        // Booking 3 from four days from now 10:00 until 14:00 (same day)
        bookingEntityList.add(new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.ACCEPTED));
        // Booking 4 was rejected (should be ignored)
        bookingEntityList.add(new BookingEntity("4", "ADF 123",
                "383203M", date2, 28, BookingStatus.REJECTED));

        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Sent booking from two days from now from 15:00 to 17:00 (same day)
        LocalDateTime date = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(15, 0));
        AddBookingRequest request = new AddBookingRequest("ADF 123", "290102L",
                date, 2);

        // Exercise
        AddBookingResponse response = bookingManagementService.addBooking(request);

        // Verify

        assertNotNull(response);
        assertEquals(response.getStatus(), BookingStatus.ACCEPTED);
        MyAssertions.assertValidUUID(response.getBookingID());

        // No teardown
    }
}
