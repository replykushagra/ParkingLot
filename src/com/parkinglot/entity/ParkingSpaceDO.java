package com.parkinglot.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import org.apache.commons.lang3.StringUtils;

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

    @DynamoDBHashKey(attributeName = "parkingSpaceId")
    private String parkingSpaceId;

    @DynamoDBAttribute(attributeName = "price")
    private double price;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParkingSpaceDO) {
            ParkingSpaceDO parkingSpace = (ParkingSpaceDO) obj;
            return StringUtils.equals(parkingSpace.getParkingSpaceId(), this.getParkingSpaceId()) &&
                Double.compare(parkingSpace.getPrice(), this.getPrice()) == 0;

        }
        return false;
    }

    @Override
    public int hashCode() {
        return parkingSpaceId.hashCode();
    }
}
