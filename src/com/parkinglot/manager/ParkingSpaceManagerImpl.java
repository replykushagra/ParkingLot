package com.parkinglot.manager;

import com.parkinglot.dao.ParkingSpaceDao;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.entity.ParkingSpaceDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingSpaceManagerImpl implements ParkingSpaceManager {

    private final ParkingSpaceDao parkingSpaceDao;
    private final BookingManager bookingManager;

    @Autowired
    ParkingSpaceManagerImpl(ParkingSpaceDao parkingSpaceDao,
                            BookingManager bookingManager) {
        this.parkingSpaceDao = parkingSpaceDao;
        this.bookingManager = bookingManager;
    }

    @Override
    public boolean isParkingSpaceAvailable(String parkingSpaceId, ParkingSpaceFilter filter) {
        return getAvailableParkingSpaces(filter).stream()
            .map(ParkingSpaceDO::getParkingSpaceId)
            .collect(Collectors.toList())
            .contains(parkingSpaceId);
    }

    @Override
    public List<ParkingSpaceDO> getAvailableParkingSpaces(ParkingSpaceFilter filter) {
        List<BookingDO> bookings = bookingManager.getBookings(filter);

        List<String> unavailableParkingSpaceIds = bookings.stream()
            .map(this::getParkingSpace)
            .map(ParkingSpaceDO::getParkingSpaceId)
            .collect(Collectors.toList());

        List<ParkingSpaceDO> parkingSpaces = parkingSpaceDao.getAllParkingSpaces();
        Map<String, ParkingSpaceDO> parkingSpacesById = parkingSpaces.stream()
            .collect(Collectors.toMap(parkingSpace -> parkingSpace.getParkingSpaceId(), parkingSpace -> parkingSpace));
        parkingSpacesById.keySet().removeAll(unavailableParkingSpaceIds);
        return new ArrayList<ParkingSpaceDO>(parkingSpacesById.values());
    }

    @Override
    public List<ParkingSpaceDO> getParkingSpaces(ParkingSpaceFilter parkingSpaceFilter) {
        List<BookingDO> bookings = bookingManager.getBookings(parkingSpaceFilter);
        return bookings.stream()
            .map(this::getParkingSpace)
            .collect(Collectors.toList());
    }

    @Override
    public ParkingSpaceDO getParkingSpaceById(String parkingSpaceId) {
        return parkingSpaceDao.getParkingSpaceById(parkingSpaceId);
    }

    private ParkingSpaceDO getParkingSpace(BookingDO booking) {
        return getParkingSpaceById(booking.getParkingSpaceId());
    }
}
