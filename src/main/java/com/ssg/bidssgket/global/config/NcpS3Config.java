package com.ssg.bidssgket.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class NcpS3Config {

    @Value("${ncp.s3.end-point}")
    private String endPoint;
    @Value("${ncp.s3.region-name}")
    private String regionName;
    @Value("${ncp.s3.bucket-name}")
    private String bucketName;
    @Value("${ncp.s3.access-key}")
    private String accessKey;
    @Value("${ncp.s3.secret-key}")
    private String secretKey;

    @Bean
    public AmazonS3Client s3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return(AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }



}

