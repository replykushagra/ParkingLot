package com.parkinglot.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamoDBTable(tableName="BOOKING")
public class BookingDO {

    @DynamoDBHashKey(attributeName = "booking_id")
    private String bookingId;

    @DynamoDBAttribute(attributeName = "parking_space_id")
    private String parkingSpaceId;

    @DynamoDBAttribute(attributeName = "booking_till")
    private long bookingTill;

    @DynamoDBAttribute(attributeName = "booking_from")
    private long bookingFrom;
}
