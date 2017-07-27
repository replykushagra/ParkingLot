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
@DynamoDBTable(tableName="PARKING_SPACE")
public class ParkingSpaceDO {

    @DynamoDBHashKey(attributeName = "parking_space_id")
    private String parkingSpaceId;

    @DynamoDBAttribute(attributeName = "price")
    private double price;
}
