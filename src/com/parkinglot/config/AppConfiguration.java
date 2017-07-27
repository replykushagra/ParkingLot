package com.parkinglot.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@PropertySource("file:application.properties")
public class AppConfiguration {

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Bean
    public InternalResourceViewResolver setupViewResolver()  {
        InternalResourceViewResolver resolver =  new InternalResourceViewResolver();
        resolver.setPrefix ("/WEB-INF/views/");
        resolver.setSuffix (".ftl");
        return resolver;
    }

    @Bean
    public DynamoDBMapper dynamoDBMapperClient() {
        AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(new BasicAWSCredentials(accessKey, secretKey));
        return new DynamoDBMapper(dynamoDB);
    }

}
