package com.parkinglot.manager;

import com.parkinglot.dao.BookingDao;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.InvalidParameterException;
import java.time.Instant;

import junit.framework.Assert;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingManagerImplTest {

    private BookingManagerImpl bookingManager;
    private BookingDao mockedBookingDao;
    private ParkingSpaceManager mockedParkingSpaceManager;

    private static final String DEFAULT_PARKING_SPACE_ID = "ParkingSpaceId";
    private static final Long TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY = 86400000L;
    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(System.currentTimeMillis());
    private static final Instant DEFAULT_TILL_DATE = Instant.ofEpochMilli(System.currentTimeMillis()
        + TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY);

    @Before
    public void init() {
        mockedBookingDao = Mockito.mock(BookingDao.class);
        mockedParkingSpaceManager = Mockito.mock(ParkingSpaceManager.class);
        bookingManager = new BookingManagerImpl(mockedBookingDao, mockedParkingSpaceManager);
    }

    @Test
    public void test_bookParkingSpace_success() {
        when(mockedParkingSpaceManager.isParkingSpaceAvailable(Mockito.anyString(), Mockito.any(ParkingSpaceFilter.class)))
            .thenReturn(true);
        bookingManager.bookParkingSpace(DEFAULT_PARKING_SPACE_ID,
            ParkingSpaceFilter.builder().fromDate(DEFAULT_FROM_DATE).toDate(DEFAULT_TILL_DATE).build());

        BookingDO booking = generateDefaultBooking();
        verify(mockedBookingDao).save(booking);
    }

    @Test(expected = InvalidParameterException.class)
    public void test_bookParkingSpace_failure() {
        when(mockedParkingSpaceManager.isParkingSpaceAvailable(Mockito.anyString(), Mockito.any(ParkingSpaceFilter.class)))
        .thenReturn(false);
        bookingManager.bookParkingSpace(DEFAULT_PARKING_SPACE_ID,
            ParkingSpaceFilter.builder().fromDate(DEFAULT_FROM_DATE).toDate(DEFAULT_TILL_DATE).build());
    }

    @Test
    public void test_getBookingById() {
        BookingDO expected = generateDefaultBooking();
        when(mockedBookingDao.getBookingById(generateBookingId())).thenReturn(generateDefaultBooking());
        Assert.assertEquals("Booking object", expected, bookingManager.getBookingById(generateBookingId()));
    }

    @Test
    public void test_gddBooking() {
        bookingManager.add(generateDefaultBooking());
        verify(mockedBookingDao).save(generateDefaultBooking());
    }

    @Test
    public void test_getBookings() {
        bookingManager.getBookings(generateDefaultParkingSpaceFilter());
        verify(mockedBookingDao).getBookings(generateDefaultParkingSpaceFilter());
    }

    private static String generateBookingId() {
        return new StringBuilder().append(DEFAULT_PARKING_SPACE_ID)
            .append(DEFAULT_FROM_DATE.toEpochMilli())
            .append(DEFAULT_TILL_DATE.toEpochMilli())
            .substring(0);
    }

    private BookingDO generateDefaultBooking() {
        return BookingDO.builder()
        .bookingFrom(DEFAULT_FROM_DATE.toEpochMilli())
        .bookingTill(DEFAULT_TILL_DATE.toEpochMilli())
        .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
        .bookingId(generateBookingId())
        .build();
    }

    private ParkingSpaceFilter generateDefaultParkingSpaceFilter() {
        return ParkingSpaceFilter.builder()
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TILL_DATE)
            .build();
    }

}
