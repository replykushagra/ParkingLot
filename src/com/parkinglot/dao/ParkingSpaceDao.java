package com.parkinglot.dao;

import com.parkinglot.entity.ParkingSpaceDO;

import java.util.List;

public interface ParkingSpaceDao {

    ParkingSpaceDO getParkingSpaceById(String parkingSpaceId);

    void saveParkingSpace(ParkingSpaceDO parkingSpace);

    List<ParkingSpaceDO> getAllParkingSpaces();
}
