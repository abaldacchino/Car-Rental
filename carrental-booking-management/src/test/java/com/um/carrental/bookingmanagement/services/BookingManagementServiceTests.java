package com.um.carrental.bookingmanagement.services;

import com.cedarsoftware.util.DeepEquals;
import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import com.um.carrental.bookingmanagement.data.repositories.BookingRepository;
import com.um.carrental.bookingmanagement.enums.BookingStatus;
import com.um.carrental.bookingmanagement.enums.BookingStatusQuery;
import com.um.carrental.bookingmanagement.exceptions.BookingNotFoundException;
import com.um.carrental.bookingmanagement.exceptions.InvalidBookingRequestException;
import com.um.carrental.bookingmanagement.helpers.MyAssertions;
import com.um.carrental.bookingmanagement.web.requests.AddBookingRequest;
import com.um.carrental.bookingmanagement.web.responses.AddBookingResponse;
import com.um.carrental.bookingmanagement.web.responses.GetBookingListResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class BookingManagementServiceTests {
    @Autowired
    BookingManagementService bookingManagementService;

    @Autowired
    ModelMapper mapper;

    @MockBean
    BookingRepository repositoryMock;

    @Test
    public void testAddValidBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));

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
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120));
        // Booking 2 from three days from now 10:00 until 12:00 (same day)
        bookingEntityList.add(new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120));
        // Booking 3 from four days from now 10:00 until 14:00 (same day)
        bookingEntityList.add(new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.ACCEPTED, 120));
        // Booking 4 was rejected (should be ignored)
        bookingEntityList.add(new BookingEntity("4", "ADF 123",
                "383203M", date2, 28, BookingStatus.REJECTED, 120));

        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Sent booking from three days from now from 15:00 to 17:00 (same day)
        LocalDateTime date = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(15, 0));
        AddBookingRequest request = new AddBookingRequest("ADF 123", "290102L",
                date, 2);

        // Exercise
        AddBookingResponse response = bookingManagementService.addBooking(request);

        // Verify

        assertNotNull(response);
        assertEquals(BookingStatus.ACCEPTED, response.getStatus());
        MyAssertions.assertValidUUID(response.getBookingID());
        verify(repositoryMock, times(1)).findAll();
        verify(repositoryMock, times(1)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddOverlapBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));

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
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120));
        // Booking 2 from three days from now 10:00 until 12:00 (same day)
        bookingEntityList.add(new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120));
        // Booking 3 from four days from now 10:00 until 14:00 (same day)
        bookingEntityList.add(new BookingEntity("3", "ADF 123",
                "383202L", date1, 4, BookingStatus.ACCEPTED, 120));
        // Booking 4 was rejected (should be ignored)
        bookingEntityList.add(new BookingEntity("4", "ADF 123",
                "383203M", date2, 28, BookingStatus.REJECTED, 120));

        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Sent booking from two days from now from 13:00 to 17:00 (same day)
        LocalDateTime date = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(13, 0));
        AddBookingRequest request = new AddBookingRequest("ADF 123", "290102L",
                date, 4);

        // Exercise
        AddBookingResponse response = bookingManagementService.addBooking(request);

        // Verify

        assertNotNull(response);
        assertEquals(BookingStatus.REJECTED, response.getStatus());
        MyAssertions.assertValidUUID(response.getBookingID());
        verify(repositoryMock, times(1)).findAll();
        verify(repositoryMock, times(1)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddPastTimeBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));

        LocalDateTime date = LocalDateTime.of(LocalDate.now().minusDays(2),
                LocalTime.of(13, 0));
        AddBookingRequest request =
                new AddBookingRequest("ADF 123", "290102L",
                date, 4);
        boolean exceptionCaught =false;

        // Exercise
        try{
            AddBookingResponse response = bookingManagementService.addBooking(request);
        }catch(InvalidBookingRequestException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(0)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddOutOfHoursStartTimeBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));
        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(2),
                LocalTime.of(6, 0));
        AddBookingRequest request =
                new AddBookingRequest("ADF 123", "290102L",
                        date, 4);
        boolean exceptionCaught =false;

        // Exercise
        try{
            AddBookingResponse response = bookingManagementService.addBooking(request);
        }catch(InvalidBookingRequestException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(0)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddOutOfHoursEndTimeBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));
        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(2),
                LocalTime.of(10, 0));
        AddBookingRequest request =
                new AddBookingRequest("ADF 123", "290102L",
                        date, 10);
        boolean exceptionCaught =false;

        // Exercise
        try{
            AddBookingResponse response = bookingManagementService.addBooking(request);
        }catch(InvalidBookingRequestException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(0)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddInvalidHoursBooking(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById and
        // vehicleExistsByNumberPlate return true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, true));
        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(2),
                LocalTime.of(10, 0));
        AddBookingRequest request =
                new AddBookingRequest("ADF 123", "290102L",
                        date, -1);
        boolean exceptionCaught =false;

        // Exercise
        try{
            AddBookingResponse response = bookingManagementService.addBooking(request);
        }catch(InvalidBookingRequestException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(0)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddBookingNonexistentVehicle(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById returns true,
        // vehicleExistsByNumberPlate returns false
        bookingManagementService.setMessaging(
                new MessagingServiceMock(-1, true));

        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 0));
        AddBookingRequest request = new AddBookingRequest("ADF 123", "290102L",
                date, 2);

        // Exercise
        AddBookingResponse response = bookingManagementService.addBooking(request);


        // Verify
        assertNotNull(response);
        assertEquals(BookingStatus.REJECTED, response.getStatus());
        MyAssertions.assertValidUUID(response.getBookingID());
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(1)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testAddBookingNonexistentCustomer(){
        // Setup
        // Modifying BookingManagementService so that customerExistsById returns false,
        // vehicleExistsByNumberPlate returns true
        bookingManagementService.setMessaging(
                new MessagingServiceMock(120, false));

        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 0));
        AddBookingRequest request = new AddBookingRequest("ADF 123", "290102L",
                date, 2);

        // Exercise
        AddBookingResponse response = bookingManagementService.addBooking(request);


        // Verify
        assertNotNull(response);
        assertEquals(BookingStatus.REJECTED, response.getStatus());
        MyAssertions.assertValidUUID(response.getBookingID());
        verify(repositoryMock, times(0)).findAll();
        verify(repositoryMock, times(1)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testGetBookingPresent(){
        // Setup
        String bookingID = "5bce8560-07a6-4750-8c09-57f30545714b";
        BookingEntity returnedEntity = new BookingEntity(bookingID, "ABC 123", "383702L",
                LocalDateTime.now(), 2, BookingStatus.ACCEPTED, 120);
        Booking expectedResponse = mapper.map(returnedEntity, Booking.class);
        when(repositoryMock.getById(bookingID)).thenReturn(returnedEntity);
        when(repositoryMock.existsById(bookingID)).thenReturn(true);

        // Exercise
        Booking response = bookingManagementService.getBooking(bookingID);

        // Verify
        assertTrue(DeepEquals.deepEquals(expectedResponse, response));
        verify(repositoryMock, times(1)).existsById(bookingID);
        verify(repositoryMock, times(1)).getById(bookingID);

        // No teardown
    }
    @Test
    public void testGetBookingNotPresent(){
        // Setup
        String bookingID = "5bce8560-07a6-4750-8c09-57f30545714b";
        BookingEntity returnedEntity = new BookingEntity(bookingID, "ABC 123", "383702L",
                LocalDateTime.now(), 2, BookingStatus.ACCEPTED, 120);
        when(repositoryMock.existsById(bookingID)).thenReturn(false);
        boolean exceptionCaught = false;
        // Exercise

        try{
            Booking response = bookingManagementService.getBooking(bookingID);
        }catch(BookingNotFoundException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(1)).existsById(bookingID);
        verify(repositoryMock, times(0)).getById(bookingID);

        // No teardown
    }

    @Test
    public void testGetBookingListAny(){
        // Setup
        BookingStatusQuery query = BookingStatusQuery.ANY;

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of bookings in system
        List<BookingEntity> bookingEntityList = new ArrayList<>();

        BookingEntity acceptedBooking1 = new BookingEntity("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120);
        BookingEntity acceptedBooking2 = new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120);
        BookingEntity cancelledBooking1 = new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.CANCELLED, 120);
        BookingEntity cancelledBooking2 = new BookingEntity("4", "ABC 109",
                "383332L", date2, 2, BookingStatus.CANCELLED, 120);
        BookingEntity rejectedBooking1 = new BookingEntity("5", "ADF 123",
                "383203M", date3, 28, BookingStatus.REJECTED, 120);
        BookingEntity rejectedBooking2 = new BookingEntity("9", "ADF 123",
                "383203M", date1, 28, BookingStatus.REJECTED, 120);

        bookingEntityList.add(acceptedBooking1);
        bookingEntityList.add(acceptedBooking2);
        bookingEntityList.add(cancelledBooking1);
        bookingEntityList.add(cancelledBooking2);
        bookingEntityList.add(rejectedBooking1);
        bookingEntityList.add(rejectedBooking2);
        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Expected booking list
        List<Booking> expectedResponse = new ArrayList<>();
        expectedResponse.add(mapper.map(acceptedBooking1, Booking.class));
        expectedResponse.add(mapper.map(acceptedBooking2, Booking.class));
        expectedResponse.add(mapper.map(cancelledBooking1, Booking.class));
        expectedResponse.add(mapper.map(cancelledBooking2, Booking.class));
        expectedResponse.add(mapper.map(rejectedBooking1, Booking.class));
        expectedResponse.add(mapper.map(rejectedBooking2, Booking.class));

        // Exercise
        List<Booking> actualResponse = bookingManagementService.getBookingList(query);

        // Verify
        assertNotNull(actualResponse);
        assertTrue(DeepEquals.deepEquals(expectedResponse, actualResponse));
        verify(repositoryMock, times(1)).findAll();
        // Teardown -- no teardown stage
    }

    @Test
    public void testGetBookingListAccepted(){
        // Setup
        BookingStatusQuery query = BookingStatusQuery.ACCEPTED;

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of bookings in system
        List<BookingEntity> bookingEntityList = new ArrayList<>();

        BookingEntity acceptedBooking1 = new BookingEntity("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120);
        BookingEntity acceptedBooking2 = new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120);
        BookingEntity cancelledBooking1 = new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.CANCELLED, 120);
        BookingEntity cancelledBooking2 = new BookingEntity("4", "ABC 109",
                "383332L", date2, 2, BookingStatus.CANCELLED, 120);
        BookingEntity rejectedBooking1 = new BookingEntity("5", "ADF 123",
                "383203M", date3, 28, BookingStatus.REJECTED, 120);
        BookingEntity rejectedBooking2 = new BookingEntity("9", "ADF 123",
                "383203M", date1, 28, BookingStatus.REJECTED, 120);

        bookingEntityList.add(acceptedBooking1);
        bookingEntityList.add(acceptedBooking2);
        bookingEntityList.add(cancelledBooking1);
        bookingEntityList.add(cancelledBooking2);
        bookingEntityList.add(rejectedBooking1);
        bookingEntityList.add(rejectedBooking2);
        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Expected booking list
        List<Booking> expectedResponse = new ArrayList<>();
        expectedResponse.add(mapper.map(acceptedBooking1, Booking.class));
        expectedResponse.add(mapper.map(acceptedBooking2, Booking.class));

        // Exercise
        List<Booking> actualResponse = bookingManagementService.getBookingList(query);

        // Verify
        assertNotNull(actualResponse);
        assertTrue(DeepEquals.deepEquals(expectedResponse, actualResponse));
        verify(repositoryMock, times(1)).findAll();
        // Teardown -- no teardown stage
    }

    @Test
    public void testGetBookingListRejected(){
        // Setup
        BookingStatusQuery query = BookingStatusQuery.REJECTED;

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of bookings in system
        List<BookingEntity> bookingEntityList = new ArrayList<>();

        BookingEntity acceptedBooking1 = new BookingEntity("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120);
        BookingEntity acceptedBooking2 = new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120);
        BookingEntity cancelledBooking1 = new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.CANCELLED, 120);
        BookingEntity cancelledBooking2 = new BookingEntity("4", "ABC 109",
                "383332L", date2, 2, BookingStatus.CANCELLED, 120);
        BookingEntity rejectedBooking1 = new BookingEntity("5", "ADF 123",
                "383203M", date3, 28, BookingStatus.REJECTED, 120);
        BookingEntity rejectedBooking2 = new BookingEntity("9", "ADF 123",
                "383203M", date1, 28, BookingStatus.REJECTED, 120);

        bookingEntityList.add(acceptedBooking1);
        bookingEntityList.add(acceptedBooking2);
        bookingEntityList.add(cancelledBooking1);
        bookingEntityList.add(cancelledBooking2);
        bookingEntityList.add(rejectedBooking1);
        bookingEntityList.add(rejectedBooking2);
        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Expected booking list
        List<Booking> expectedResponse = new ArrayList<>();
        expectedResponse.add(mapper.map(rejectedBooking1, Booking.class));
        expectedResponse.add(mapper.map(rejectedBooking2, Booking.class));

        // Exercise
        List<Booking> actualResponse = bookingManagementService.getBookingList(query);

        // Verify
        assertNotNull(actualResponse);
        assertTrue(DeepEquals.deepEquals(expectedResponse, actualResponse));
        verify(repositoryMock, times(1)).findAll();
        // Teardown -- no teardown stage
    }

    @Test
    public void testGetBookingListCancelled(){
        // Setup
        BookingStatusQuery query = BookingStatusQuery.CANCELLED;

        // Creating dates for existing bookings in system
        LocalDate dateTwoDaysFromNow = LocalDate.now().plusDays(2);
        LocalDate dateThreeDaysFromNow = LocalDate.now().plusDays(3);
        LocalDate dateFourDaysFromNow = LocalDate.now().plusDays(4);
        LocalDateTime date1 = LocalDateTime.of(dateTwoDaysFromNow, LocalTime.of(14, 0));
        LocalDateTime date2 = LocalDateTime.of(dateThreeDaysFromNow, LocalTime.of(10, 0));
        LocalDateTime date3 = LocalDateTime.of(dateFourDaysFromNow, LocalTime.of(10, 0));

        // Creating booking entity list of bookings in system
        List<BookingEntity> bookingEntityList = new ArrayList<>();

        BookingEntity acceptedBooking1 = new BookingEntity("1", "ABC 123",
                "383702L", date1, 19, BookingStatus.ACCEPTED, 120);
        BookingEntity acceptedBooking2 = new BookingEntity("2", "ABC 193",
                "383332L", date2, 2, BookingStatus.ACCEPTED, 120);
        BookingEntity cancelledBooking1 = new BookingEntity("3", "ADF 123",
                "383202L", date3, 4, BookingStatus.CANCELLED, 120);
        BookingEntity cancelledBooking2 = new BookingEntity("4", "ABC 109",
                "383332L", date2, 2, BookingStatus.CANCELLED, 120);
        BookingEntity rejectedBooking1 = new BookingEntity("5", "ADF 123",
                "383203M", date3, 28, BookingStatus.REJECTED, 120);
        BookingEntity rejectedBooking2 = new BookingEntity("9", "ADF 123",
                "383203M", date1, 28, BookingStatus.REJECTED, 120);

        bookingEntityList.add(acceptedBooking1);
        bookingEntityList.add(acceptedBooking2);
        bookingEntityList.add(cancelledBooking1);
        bookingEntityList.add(cancelledBooking2);
        bookingEntityList.add(rejectedBooking1);
        bookingEntityList.add(rejectedBooking2);
        when(repositoryMock.findAll()).thenReturn(bookingEntityList);

        // Expected booking list
        List<Booking> expectedResponse = new ArrayList<>();
        expectedResponse.add(mapper.map(cancelledBooking1, Booking.class));
        expectedResponse.add(mapper.map(cancelledBooking2, Booking.class));

        // Exercise
        List<Booking> actualResponse = bookingManagementService.getBookingList(query);

        // Verify
        assertNotNull(actualResponse);
        assertTrue(DeepEquals.deepEquals(expectedResponse, actualResponse));
        verify(repositoryMock, times(1)).findAll();
        // Teardown -- no teardown stage
    }

    @Test
    public void testCancelBookingPresent(){
        // Setup
        String bookingID = "3988828b-52d0-4be8-9e19-24f963cc9f11";
        when(repositoryMock.existsById(bookingID)).thenReturn(true);


        LocalDateTime date = LocalDateTime.of(LocalDate.now().plusDays(2),
                LocalTime.of(14, 0));
        BookingEntity booking = new BookingEntity(bookingID, "ABC 123",
                "383702L", date, 19, BookingStatus.ACCEPTED, 120);
        when(repositoryMock.getById(bookingID)).thenReturn(booking);

        // Exercise
        bookingManagementService.cancelBooking(bookingID);

        // Verify
        verify(repositoryMock, times(1)).existsById(bookingID);
        verify(repositoryMock, times(1)).getById(bookingID);
        verify(repositoryMock, times(1)).save(any(BookingEntity.class));

        // No teardown
    }

    @Test
    public void testCancelBookingNotPresent(){
        // Setup
        String bookingID = "3988828b-52d0-4be8-9e19-24f963cc9f11";
        when(repositoryMock.existsById(bookingID)).thenReturn(false);
        boolean exceptionCaught =false;

        // Exercise
        try{
            bookingManagementService.cancelBooking(bookingID);
        }catch(BookingNotFoundException e){
            exceptionCaught=true;
        }

        // Verify
        assertTrue(exceptionCaught);
        verify(repositoryMock, times(1)).existsById(bookingID);
        verify(repositoryMock, times(0)).getById(bookingID);
        verify(repositoryMock, times(0)).save(any(BookingEntity.class));

        // No teardown
    }
}
