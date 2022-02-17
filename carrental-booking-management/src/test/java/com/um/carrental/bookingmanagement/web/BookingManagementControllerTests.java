package com.um.carrental.bookingmanagement.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.services.Booking;
import com.um.carrental.bookingmanagement.services.BookingManagementService;
import com.um.carrental.bookingmanagement.web.controllers.BookingManagementController;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.requests.GetBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
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

    //String numberPlate;
    //
    //    String customerID;
    //
    //    LocalDateTime startTime;
    //
    //    int hours;
    //
    //    BookingStatus status;

    @Test
    public void testGetBookingValid(){
        // Setup
        GetBookingRequest request =
                new GetBookingRequest("3988828b-52d0-4be8-9e19-24f963cc9f11");

        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(1),
                LocalTime.of(14, 0));

        GetBookingResponse expectedResponse =
                new GetBookingResponse("3988828b-52d0-4be8-9e19-24f963cc9f11",
                        "ABC 123", "383702L", date, 3, BookingStatus.ACCEPTED);
        Booking returnedBooking = mapper.map(expectedResponse, Booking.class);
        when(bookingManagementServiceMock.getBooking(request)).thenReturn(returnedBooking);

        // Exercise
        GetBookingResponse response =
                bookingManagementController.getBooking(request.getBookingID());

        // Verify
        assertNotNull(response);
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(bookingManagementServiceMock, times(1)).getBooking(request);

        // Teardown -- no teardown stage
    }
}
