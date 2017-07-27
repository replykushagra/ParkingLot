package com.parkinglot.filter;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ParkingSpaceFilter {

    Instant fromDate;
    Instant toDate;
}
