package com.company.youtubeclone.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAmazonClient {
    AWSCredentials credentials = new BasicAWSCredentials(
            "AKIARDCTR7K3UNND",
            "8oqdCIQ4w"
    );

    @Bean
    public AmazonS3Client amazonS3Client(){
        return (AmazonS3Client) AmazonS3ClientBuilder.standard().withCredentials(new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return credentials;
            }

            @Override
            public void refresh() {

            }
        }).withRegion(Regions.AP_SOUTH_1).build();
    }

}
