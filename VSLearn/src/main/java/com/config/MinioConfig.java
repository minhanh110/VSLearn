package com.config;

import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MinioConfig {
    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // Check if bucket exists
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                // Create bucket if it doesn't exist
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.info("Created bucket: {}", bucketName);
            }

            // Set bucket policy to allow public read access
            String policy = String.format(
                "{\n" +
                "    \"Version\": \"2012-10-17\",\n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Effect\": \"Allow\",\n" +
                "            \"Principal\": \"*\",\n" +
                "            \"Action\": [\n" +
                "                \"s3:GetObject\"\n" +
                "            ],\n" +
                "            \"Resource\": [\n" +
                "                \"arn:aws:s3:::%s/*\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}", bucketName);

            minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(policy)
                    .build()
            );
            logger.info("Set bucket policy for public read access on bucket: {}", bucketName);

            return minioClient;
        } catch (Exception e) {
            logger.error("Error initializing MinIO client: {}", e.getMessage());
            throw new RuntimeException("Error initializing MinIO client", e);
        }
    }

    @Bean
    public String minioBucketName() {
        return bucketName;
    }

    @Bean
    public String minioEndpoint() {
        return endpoint;
    }
} 