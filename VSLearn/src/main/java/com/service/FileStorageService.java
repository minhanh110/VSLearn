package com.service;

import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final MinioClient minioClient;
    private final String bucketName;
    private final String endpoint;

    @Autowired
    public FileStorageService(MinioClient minioClient, String minioBucketName, String minioEndpoint) {
        this.minioClient = minioClient;
        this.bucketName = minioBucketName;
        this.endpoint = minioEndpoint;
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            logger.info("File uploaded successfully: {}", fileName);
            return fileName;
        } catch (Exception e) {
            logger.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
            logger.info("File deleted successfully: {}", fileName);
        } catch (Exception e) {
            logger.error("Error deleting file: {}", e.getMessage());
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public String getFileUrl(String fileName) {
        try {
            return endpoint + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            logger.error("Error getting file URL: {}", e.getMessage());
            throw new RuntimeException("Error getting file URL", e);
        }
    }

    public List<String> listFiles() {
        try {
            List<String> fileNames = new ArrayList<>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
            for (Result<Item> result : results) {
                fileNames.add(result.get().objectName());
            }
            return fileNames;
        } catch (Exception e) {
            logger.error("Error listing files: {}", e.getMessage());
            throw new RuntimeException("Error listing files", e);
        }
    }

    public InputStream getFile(String fileName) {
        try {
            return minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
        } catch (Exception e) {
            logger.error("Error getting file: {}", e.getMessage());
            throw new RuntimeException("Error getting file", e);
        }
    }
} 