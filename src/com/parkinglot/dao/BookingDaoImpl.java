package com.parkinglot.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDaoImpl implements BookingDao {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public BookingDaoImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public BookingDO getBookingById(String bookingId) {
        return dynamoDBMapper.load(BookingDO.class, bookingId);
    }

    @Override
    public void save(BookingDO booking) {
        dynamoDBMapper.save(booking);
    }

    @Override
    public List<BookingDO> getBookings(ParkingSpaceFilter filter) {
        Map<String, AttributeValue> attributeValues = new HashMap<>();
        // TODO explore dynamoDBMapper to query based on non key attributes
        return Collections.EMPTY_LIST;
    }

    private AttributeValue createAttributeValueFromInstant(Instant instant) {
        return new AttributeValue().withN(String.valueOf(instant.toEpochMilli()));
    }
}
