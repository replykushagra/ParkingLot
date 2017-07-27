package com.parkinglot.manager;

import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import java.util.List;

public interface BookingManager {

    String bookParkingSpace(String parkingSpaceId, ParkingSpaceFilter filter);

    BookingDO getBookingById(String bookingId);

    void add(BookingDO booking);

    List<BookingDO> getBookings(ParkingSpaceFilter filter);
}
