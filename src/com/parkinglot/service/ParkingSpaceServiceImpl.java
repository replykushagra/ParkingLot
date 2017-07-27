package com.parkinglot.service;

import com.parkinglot.entity.ParkingSpaceDO;
import com.parkinglot.filter.ParkingSpaceEarningsFilter;
import com.parkinglot.filter.ParkingSpaceFilter;

import java.util.List;

public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    @Override
    public List<ParkingSpaceDO> getParkingSpaces(ParkingSpaceFilter parkingSpaceFilter) {
        return null;
    }

    @Override
    public List<ParkingSpaceDO> getAllEmptyParkingSpaces(String lotNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getPrice(String parkingSpaceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double getEarnings(ParkingSpaceEarningsFilter parkingSpaceEarningFilter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAvailable(String parkingSpaceId) {
        // TODO Auto-generated method stub
        return false;
    }

}
