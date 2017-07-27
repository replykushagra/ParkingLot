package com.parkinglot.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public AmazonDynamoDB getAmazonDynamoDBInstance() {
        return DynamoDBEmbedded.create().amazonDynamoDB();
    }
}
