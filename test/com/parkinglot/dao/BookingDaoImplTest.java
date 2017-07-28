package com.parkinglot.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.parkinglot.entity.BookingDO;
import com.parkinglot.filter.ParkingSpaceFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import junit.framework.Assert;

public class BookingDaoImplTest {

    private static final String TABLE_NAME="BOOKING";
    private static final String BOOKING_ID_ATTRIBUTE_NAME = "bookingId";
    private static final String DEFAULT_PARKING_SPACE_ID = "ParkingSpaceId";
    private static final String PARKING_SPACE_ID_1 = "ParkingSpaceId1";
    private static final String PARKING_SPACE_ID_2 = "ParkingSpaceId2";
    private static final Long TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY = 86400000L;
    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(System.currentTimeMillis());
    private static final Instant DEFAULT_TILL_DATE = Instant.ofEpochMilli(System.currentTimeMillis()
        + TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY);
    private static final Instant TWO_DAYS_FROM_NOW = Instant.ofEpochMilli(System.currentTimeMillis()
        + TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY * 3);
    private static final Instant THREE_DAYS_FROM_NOW = Instant.ofEpochMilli(System.currentTimeMillis()
        + TOTAL_NUMBER__OF_MILLISECONDS_IN_A_DAY * 3);

    private DynamoDBMapper dynamoDBMapper;
    private AmazonDynamoDB dynamoDB;
    private BookingDaoImpl bookingDao;

    @Before
    public void init() {
        dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        bookingDao = new BookingDaoImpl(dynamoDBMapper);
        createTable();
        addRecords();
    }

    @After
    public void teardown() {
        dynamoDB.deleteTable(TABLE_NAME);
    }

    @Test
    public void test_getBookingById() {
        BookingDO booking = BookingDO.builder()
            .bookingFrom(DEFAULT_FROM_DATE.toEpochMilli())
            .bookingTill(DEFAULT_TILL_DATE.toEpochMilli())
            .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
            .bookingId(generateBookingId(DEFAULT_PARKING_SPACE_ID, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE))
            .build();

        Assert.assertEquals("Booking object", booking, bookingDao.getBookingById(
            generateBookingId(DEFAULT_PARKING_SPACE_ID, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE)));
    }

    @Test
    public void test_save() {
        BookingDO booking = BookingDO.builder()
            .bookingFrom(DEFAULT_FROM_DATE.toEpochMilli())
            .bookingTill(DEFAULT_TILL_DATE.toEpochMilli())
            .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
            .bookingId(generateBookingId(PARKING_SPACE_ID_2, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE))
            .build();
        bookingDao.save(booking);

        Assert.assertEquals("Booking object", booking, bookingDao.getBookingById(
            generateBookingId(PARKING_SPACE_ID_2, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE)));
    }

    @Test
    public void test_getBookings() {
        ParkingSpaceFilter filter = ParkingSpaceFilter.builder()
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TILL_DATE)
            .build();
        Assert.assertEquals("Number of bookings", 2, bookingDao.getBookings(filter).size());
        List<String> bookingIds = bookingDao.getBookings(filter)
            .stream()
            .map(BookingDO::getBookingId)
            .collect(Collectors.toList());
        Assert.assertTrue(bookingIds.contains(generateBookingId(DEFAULT_PARKING_SPACE_ID, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE)));
        Assert.assertTrue(bookingIds.contains(generateBookingId(PARKING_SPACE_ID_1, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE)));
    }

    private void addRecords() {
        BookingDO booking1 = BookingDO.builder()
            .bookingFrom(DEFAULT_FROM_DATE.toEpochMilli())
            .bookingTill(DEFAULT_TILL_DATE.toEpochMilli())
            .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
            .bookingId(generateBookingId(DEFAULT_PARKING_SPACE_ID, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE))
            .build();

        BookingDO booking2 = BookingDO.builder()
            .bookingFrom(DEFAULT_FROM_DATE.toEpochMilli())
            .bookingTill(DEFAULT_TILL_DATE.toEpochMilli())
            .parkingSpaceId(PARKING_SPACE_ID_1)
            .bookingId(generateBookingId(PARKING_SPACE_ID_1, DEFAULT_FROM_DATE, DEFAULT_TILL_DATE))
            .build();

        BookingDO booking3 = BookingDO.builder()
            .bookingFrom(TWO_DAYS_FROM_NOW.toEpochMilli())
            .bookingTill(THREE_DAYS_FROM_NOW.toEpochMilli())
            .parkingSpaceId(PARKING_SPACE_ID_1)
            .bookingId(generateBookingId(PARKING_SPACE_ID_1, TWO_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW))
            .build();

        dynamoDBMapper.save(booking1);
        dynamoDBMapper.save(booking2);
        dynamoDBMapper.save(booking3);
    }

    private static String generateBookingId(String parkingSpaceId, Instant fromDate, Instant toDate) {
        return new StringBuilder().append(parkingSpaceId)
            .append(fromDate.toEpochMilli())
            .append(toDate.toEpochMilli())
            .substring(0);
    }

    private void createTable() {
        CreateTableRequest request = new CreateTableRequest().withTableName(TABLE_NAME);
        createAttribute(request, BOOKING_ID_ATTRIBUTE_NAME, ScalarAttributeType.S);
        addKeySchema(request, BOOKING_ID_ATTRIBUTE_NAME, KeyType.HASH);
        setThroughputLimits(request);
        dynamoDB.createTable(request);
    }

    private static void createAttribute(CreateTableRequest request, String columnName, ScalarAttributeType type) {
        request.withAttributeDefinitions(new AttributeDefinition().withAttributeName(columnName).withAttributeType(type));
    }

    private static void addKeySchema(CreateTableRequest request, String attribute, KeyType keyType) {
        request.withKeySchema(new KeySchemaElement().withAttributeName(attribute).withKeyType(keyType));
    }

    private static void setThroughputLimits(CreateTableRequest request) {
        request.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(10L));
    }

}
