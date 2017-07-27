package com.parkinglot.manager;

import com.parkinglot.entity.ParkingSpaceDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import java.util.List;

public interface ParkingSpaceManager {

    List<ParkingSpaceDO> getAvailableParkingSpaces(ParkingSpaceFilter filter);

    boolean isParkingSpaceAvailable(String parkingSpaceId, ParkingSpaceFilter filter);

    ParkingSpaceDO getParkingSpaceById(String parkingSpaceId);

    List<ParkingSpaceDO> getParkingSpaces(ParkingSpaceFilter parkingSpaceFilter);
}
