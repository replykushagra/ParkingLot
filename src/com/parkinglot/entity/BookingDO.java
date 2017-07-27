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
@DynamoDBTable(tableName="BOOKING")
public class BookingDO {

    @DynamoDBHashKey(attributeName = "booking_id")
    private String bookingId;

    @DynamoDBAttribute(attributeName = "parking_space_id")
    private String parkingSpaceId;

    @DynamoDBAttribute(attributeName = "booking_till")
    private Long bookingTill;

    @DynamoDBAttribute(attributeName = "booking_from")
    private Long bookingFrom;

    public String generateHash() {
        return new StringBuilder().append(this.parkingSpaceId)
            .append(this.bookingFrom)
            .append(this.bookingTill)
            .substring(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookingDO) {
            BookingDO booking = (BookingDO) obj;
            return booking.getBookingFrom().compareTo(this.getBookingFrom()) == 0 &&
                booking.getBookingTill().compareTo(this.getBookingTill()) == 0 &&
            StringUtils.equals(booking.getBookingId(), this.getBookingId()) &&
            StringUtils.equals(booking.getParkingSpaceId(), this.getParkingSpaceId());

        }
        return false;
    }

    @Override
    public int hashCode() {
        return bookingId.hashCode();
    }
}
