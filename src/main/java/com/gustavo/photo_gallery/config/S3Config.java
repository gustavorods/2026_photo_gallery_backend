package com.gustavo.photo_gallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {

        String accessKey = System.getenv("APP_AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("APP_AWS_SECRET_ACCESS_KEY");

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }
}