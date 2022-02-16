package com.um.carrental.bookingmanagement.web;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.services.BookingManagementService;
import com.um.carrental.bookingmanagement.web.controllers.BookingManagementController;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class BookingManagementControllerTests {
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
}
