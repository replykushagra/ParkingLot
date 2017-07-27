package com.parkinglot.filter;

import java.time.Instant;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParkingSpaceFilter {

    Instant fromDate;
    Instant toDate;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParkingSpaceFilter) {
            ParkingSpaceFilter filter = (ParkingSpaceFilter) obj;
            return filter.getFromDate().compareTo(this.fromDate) == 0
                && filter.getToDate().compareTo(this.getToDate()) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return fromDate.hashCode() + toDate.hashCode();
    }
}
