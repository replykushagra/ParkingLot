package com.parkinglot.dao;

import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import java.util.List;

public interface BookingDao {

    BookingDO getBookingById(String bookingId);

    void save(BookingDO booking);

    List<BookingDO> getBookings(ParkingSpaceFilter filter);
}
