package com.parkinglot.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.parkinglot.entity.ParkingSpaceDO;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ParkingSpaceDaoImpl implements ParkingSpaceDao {

    @Autowired
    DynamoDBMapper mapper;

    @Override
    public ParkingSpaceDO getParkingSpaceById(String parkingSpaceId) {
        return mapper.load(ParkingSpaceDO.class, parkingSpaceId);
    }

    @Override
    public void saveParkingSpace(ParkingSpaceDO parkingSpace) {
        mapper.save(parkingSpace);
    }

    @Override
    public List<ParkingSpaceDO> getAllParkingSpaces() {
        /* 1. Under the assumption that only one parking lot is supported. Might need to revisit if multiple parking
           lots are supported
           2. Might need to add pagination.
           3. This call to getAllParkingSpaces will eventually be async
        */
        return mapper.scan(ParkingSpaceDO.class, new DynamoDBScanExpression());
    }
}
