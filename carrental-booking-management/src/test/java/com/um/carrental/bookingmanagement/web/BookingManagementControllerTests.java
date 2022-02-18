package com.um.carrental.bookingmanagement.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.enums.BookingStatusQuery;
import com.um.carrental.bookingmanagement.services.Booking;
import com.um.carrental.bookingmanagement.services.BookingManagementService;
import com.um.carrental.bookingmanagement.web.controllers.BookingManagementController;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import com.um.carrental.bookingmanagement.web.responses.GetBookingListResponse;
import com.um.carrental.bookingmanagement.web.responses.GetBookingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class BookingManagementControllerTests {
    @Autowired
    ModelMapper mapper;

    @Autowired
    BookingManagementController bookingManagementController;

    @MockBean
    BookingManagementService bookingManagementServiceMock;

    @Test
    public void testAddBookingValid(){
        // Setup
        AddBookingRequest request = new AddBookingRequest();
        String bookingID = UUID.randomUUID().toString();
        AddBookingResponse expectedResponse =
                new AddBookingResponse(bookingID, BookingStatus.ACCEPTED);
        when(bookingManagementServiceMock.addBooking(request)).thenReturn(expectedResponse);

        // Exercise
        AddBookingResponse response =
                bookingManagementController.addBooking(request);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(bookingManagementServiceMock, times(1)).addBooking(request);

        // Teardown -- no teardown stage
    }

    @Test
    public void testGetBookingValid(){
        // Setup
        String bookingID = "3988828b-52d0-4be8-9e19-24f963cc9f11";

        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(1),
                LocalTime.of(14, 0));

        GetBookingResponse expectedResponse =
                new GetBookingResponse(bookingID,"ABC 123",
                        "383702L", date, 3, BookingStatus.ACCEPTED, 500);
        Booking returnedBooking = mapper.map(expectedResponse, Booking.class);
        when(bookingManagementServiceMock.getBooking(bookingID)).thenReturn(returnedBooking);

        // Exercise
        GetBookingResponse response =
                bookingManagementController.getBooking(bookingID);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(bookingManagementServiceMock, times(1)).getBooking(bookingID);

        // Teardown -- no teardown stage
    }

    @Test
    public void testGetBookingList(){
        // Setup
        BookingStatusQuery query = BookingStatusQuery.ANY;

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of existing accepted bookings in system
        List<Booking> bookingList = new ArrayList<>();
        // bookingID, numberPlate, customerID, startTime, hours, status

        // Booking 1 from two days from now 14:00 until 9:00 (next day)
        bookingList.add(new Booking("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120));
        // Booking 2 from three days from now 10:00 until 12:00 (same day)
        bookingList.add(new Booking("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120));
        // Booking 3 from four days from now 10:00 until 14:00 (same day)
        bookingList.add(new Booking("3", "ADF 123",
                "383202L", date1, 4, BookingStatus.ACCEPTED, 120));
        // Booking 4 was rejected (should be ignored)
        bookingList.add(new Booking("4", "ADF 123",
                "383203M", date2, 28, BookingStatus.REJECTED, 120));
        when(bookingManagementServiceMock.getBookingList(query)).thenReturn(bookingList);
        GetBookingListResponse expectedResponse = new GetBookingListResponse(bookingList);

        // Exercise
        GetBookingListResponse response = bookingManagementController.getBookingList(query);

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(bookingManagementServiceMock, times(1)).getBookingList(query);

        // Teardown -- no teardown stage
    }

    @Test
    public void testCancelBooking(){
        // Setup
        String bookingID = "3988828b-52d0-4be8-9e19-24f963cc9f11";

        // Exercise
        bookingManagementController.cancelBooking(bookingID);

        // Verify
        verify(bookingManagementServiceMock, times(1))
                .cancelBooking(bookingID);

        // No teardown
    }
}
