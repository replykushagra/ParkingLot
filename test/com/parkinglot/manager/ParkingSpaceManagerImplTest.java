package com.parkinglot.manager;

import com.parkinglot.dao.ParkingSpaceDao;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.entity.ParkingSpaceDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import static org.mockito.Mockito.when;

public class ParkingSpaceManagerImplTest {

    ParkingSpaceManagerImpl parkingSpaceManager;
    ParkingSpaceDao mockedParkingSpaceDao;
    BookingManager mockedBookingManager;

    private static final String DEFAULT_PARKING_SPACE_ID = "ParkingSpaceId";
    private static final String PARKING_SPACE_ID_1 = "ParkingSpaceId1";
    private static final Long TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY = 86400000L;
    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(System.currentTimeMillis());
    private static final Instant DEFAULT_TILL_DATE = Instant.ofEpochMilli(System.currentTimeMillis()
        + TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY);

    @Before
    public void init() {
        mockedParkingSpaceDao = Mockito.mock(ParkingSpaceDao.class);
        mockedBookingManager = Mockito.mock(BookingManager.class);
        parkingSpaceManager = new ParkingSpaceManagerImpl(mockedParkingSpaceDao, mockedBookingManager);
    }

    @Test
    public void test_isParkingSpaceAvailable() {
        when(mockedBookingManager.getBookings(generateDefaultParkingSpaceFilter())).thenReturn(getBookings());
        when(mockedParkingSpaceDao.getAllParkingSpaces()).thenReturn(getAllParkingSpaces());
        when(mockedParkingSpaceDao.getParkingSpaceById(DEFAULT_PARKING_SPACE_ID)).thenReturn(getAllParkingSpaces().get(0));

        Assert.assertTrue(parkingSpaceManager.isParkingSpaceAvailable(PARKING_SPACE_ID_1,
            generateDefaultParkingSpaceFilter()));
    }

    @Test
    public void test_getParkingSpaces() {
        when(mockedBookingManager.getBookings(generateDefaultParkingSpaceFilter())).thenReturn(getBookings());
        when(mockedParkingSpaceDao.getParkingSpaceById(DEFAULT_PARKING_SPACE_ID)).thenReturn(getAllParkingSpaces().get(0));

        Assert.assertEquals(" Parking spaces ", Arrays.asList(getAllParkingSpaces().get(0)),
            parkingSpaceManager.getParkingSpaces(generateDefaultParkingSpaceFilter()));
    }

    @Test
    public void test_getParkingSpaceById() {
        when(mockedParkingSpaceDao.getParkingSpaceById(DEFAULT_PARKING_SPACE_ID)).thenReturn(getAllParkingSpaces().get(0));
        Assert.assertEquals(" Parking spaces ", getAllParkingSpaces().get(0),
            parkingSpaceManager.getParkingSpaceById(DEFAULT_PARKING_SPACE_ID));
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

    public List<BookingDO> getBookings() {
        return Arrays.asList(generateDefaultBooking());
    }

    public List<ParkingSpaceDO> getAllParkingSpaces() {
        return Arrays.asList(
            ParkingSpaceDO.builder()
                .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
                .build(),
            ParkingSpaceDO.builder()
                .parkingSpaceId(PARKING_SPACE_ID_1)
                .build()
        );
    }
}
