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
import com.parkinglot.entity.ParkingSpaceDO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParkingSpaceDaoImplTest {

    private static final String TABLE_NAME="PARKING_SPACE";
    private static final String PARKING_SPACE_ID_ATTRIBUTE_NAME = "parkingSpaceId";
    private static final String DEFAULT_PARKING_SPACE_ID = "ParkingSpaceId";
    private static final String PARKING_SPACE_ID_1 = "ParkingSpaceId1";
    private static final String PARKING_SPACE_ID_2 = "ParkingSpaceId2";

    private ParkingSpaceDaoImpl parkingSpaceDao;
    private DynamoDBMapper dynamoDBMapper;
    private AmazonDynamoDB dynamoDB;

    @Before
    public void init() {
        dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        parkingSpaceDao = new ParkingSpaceDaoImpl(dynamoDBMapper);
        createTable();
        addRecords();
    }

    @After
    public void teardown() {
        dynamoDB.deleteTable(TABLE_NAME);
    }

    @Test
    public void test_getParkingSpaceById() {
        Assert.assertEquals("Parking space",
            ParkingSpaceDO.builder()
                .parkingSpaceId(DEFAULT_PARKING_SPACE_ID).build(),
            parkingSpaceDao.getParkingSpaceById(DEFAULT_PARKING_SPACE_ID));

        Assert.assertNull(parkingSpaceDao.getParkingSpaceById("any random string"));
    }

    @Test
    public void test_saveParkingSpace() {
        parkingSpaceDao.saveParkingSpace(ParkingSpaceDO.builder().parkingSpaceId("P1").build());
        Assert.assertEquals("Parking space",
            ParkingSpaceDO.builder().parkingSpaceId("P1").build(), parkingSpaceDao.getParkingSpaceById("P1"));
    }

    @Test
    public void test_getAllParkingSpaces() {
        List<ParkingSpaceDO> allParkingSpaces = parkingSpaceDao.getAllParkingSpaces();
        Assert.assertTrue(allParkingSpaces.size() == 3);
    }

    private void createTable() {
        CreateTableRequest request = new CreateTableRequest().withTableName(TABLE_NAME);
        createAttribute(request, PARKING_SPACE_ID_ATTRIBUTE_NAME, ScalarAttributeType.S);
        addKeySchema(request, PARKING_SPACE_ID_ATTRIBUTE_NAME, KeyType.HASH);
        setThroughputLimits(request);
        dynamoDB.createTable(request);
    }

    private void addRecords() {
        ParkingSpaceDO parkingSpace1 = ParkingSpaceDO.builder()
            .parkingSpaceId(DEFAULT_PARKING_SPACE_ID)
            .build();

        ParkingSpaceDO parkingSpace2 = ParkingSpaceDO.builder()
            .parkingSpaceId(PARKING_SPACE_ID_1)
            .build();

        ParkingSpaceDO parkingSpace3 = ParkingSpaceDO.builder()
            .parkingSpaceId(PARKING_SPACE_ID_2)
            .build();

        dynamoDBMapper.save(parkingSpace1);
        dynamoDBMapper.save(parkingSpace2);
        dynamoDBMapper.save(parkingSpace3);
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
