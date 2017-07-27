package com.parkinglot.service;

import com.parkinglot.entity.ParkingSpaceDO;
import com.parkinglot.filter.ParkingSpaceEarningsFilter;
import com.parkinglot.filter.ParkingSpaceFilter;

import java.util.List;

public interface ParkingSpaceService {

    List<ParkingSpaceDO> getParkingSpaces(ParkingSpaceFilter parkingSpaceFilter);

    List<ParkingSpaceDO> getAllEmptyParkingSpaces(String lotNumber);

    Double getPrice(String parkingSpaceId);

    Double getEarnings(ParkingSpaceEarningsFilter parkingSpaceEarningFilter);

    boolean isAvailable(String parkingSpaceId);
}
