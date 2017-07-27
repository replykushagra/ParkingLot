package com.parkinglot.manager;

import com.parkinglot.dao.BookingDao;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.util.List;

public class BookingManagerImpl implements BookingManager {

    private final BookingDao bookingDao;
    private final ParkingSpaceManager parkingSpaceManager;

    @Autowired
    public BookingManagerImpl(BookingDao bookingDao,
                              ParkingSpaceManager parkingSpaceManager) {
        this.bookingDao = bookingDao;
        this.parkingSpaceManager = parkingSpaceManager;
    }

    @Override
    public String bookParkingSpace(String parkingSpaceId, ParkingSpaceFilter filter) {
        if (parkingSpaceManager.isParkingSpaceAvailable(parkingSpaceId, filter)) {
            BookingDO booking = new BookingDO();
            booking.setBookingFrom(filter.getFromDate().toEpochMilli());
            booking.setBookingTill(filter.getToDate().toEpochMilli());
            booking.setParkingSpaceId(parkingSpaceId);
            // Need a better primary key here
            booking.setBookingId(booking.generateHash());
            bookingDao.save(booking);
            return parkingSpaceId;
        }
        throw new InvalidParameterException("The parking space is not available in the given time range");
    }

    @Override
    public BookingDO getBookingById(String bookingId) {
        return bookingDao.getBookingById(bookingId);
    }

    @Override
    public void add(BookingDO booking) {
        bookingDao.save(booking);
    }

    @Override
    public List<BookingDO> getBookings(ParkingSpaceFilter filter) {
        return bookingDao.getBookings(filter);
    }
}
