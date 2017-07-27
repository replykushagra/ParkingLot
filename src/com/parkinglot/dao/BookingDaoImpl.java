package com.parkinglot.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDaoImpl implements BookingDao {

    @Autowired
    DynamoDBMapper dynamoDBMapper;

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
        attributeValues.put("bookingTill", createAttributeValueFromInstant(filter.getToDate()));
        attributeValues.put("bookingFrom", createAttributeValueFromInstant(filter.getFromDate()));

        DynamoDBQueryExpression<BookingDO> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression
            .withKeyConditionExpression(
                "(booking_from <= :bookingfrom and booking_till >= :bookingFrom) or (booking_from <= :bookingTill and booking_till >= :bookingTill)")
            .withExpressionAttributeValues(attributeValues);
        return dynamoDBMapper.query(BookingDO.class, queryExpression);
    }

    private AttributeValue createAttributeValueFromInstant(Instant instant) {
        return new AttributeValue().withN(String.valueOf(instant.toEpochMilli()));
    }
}
