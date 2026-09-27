package com.gustavo.photo_gallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {

        System.out.println(
                "AWS_ACCESS_KEY_ID exists: " +
                        (System.getenv("AWS_ACCESS_KEY_ID") != null)
        );

        System.out.println(
                "AWS_SECRET_ACCESS_KEY exists: " +
                        (System.getenv("AWS_SECRET_ACCESS_KEY") != null)
        );

        System.out.println(
                "AWS_REGION exists: " +
                        (System.getenv("AWS_REGION") != null)
        );

        return S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}