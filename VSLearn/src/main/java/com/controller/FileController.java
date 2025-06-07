package com.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@Api(tags = "File Management", description = "APIs for managing files in MinIO storage")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    @ApiOperation(value = "Upload a file", notes = "Uploads a file to MinIO storage")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "File uploaded successfully"),
        @ApiResponse(code = 400, message = "Invalid file or upload failed")
    })
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.uploadFile(file);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            logger.error("Error uploading file: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
        }
    }

    @DeleteMapping("/{fileName}")
    @ApiOperation(value = "Delete a file", notes = "Deletes a file from MinIO storage")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "File deleted successfully"),
        @ApiResponse(code = 400, message = "File deletion failed")
    })
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            fileStorageService.deleteFile(fileName);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting file: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting file: " + e.getMessage());
        }
    }

    @GetMapping("/{fileName}")
    @ApiOperation(value = "Download a file", notes = "Downloads a file from MinIO storage")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "File downloaded successfully"),
        @ApiResponse(code = 400, message = "File download failed")
    })
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String fileName) {
        try {
            InputStream inputStream = fileStorageService.getFile(fileName);
            InputStreamResource resource = new InputStreamResource(inputStream);
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        } catch (Exception e) {
            logger.error("Error getting file: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/url/{fileName}")
    @ApiOperation(value = "Get file URL", notes = "Gets the public URL for a file in MinIO storage")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "File URL retrieved successfully"),
        @ApiResponse(code = 400, message = "Failed to get file URL")
    })
    public ResponseEntity<String> getFileUrl(@PathVariable String fileName) {
        try {
            String url = fileStorageService.getFileUrl(fileName);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            logger.error("Error getting file URL: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error getting file URL: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "List all files", notes = "Lists all files in MinIO storage")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Files listed successfully"),
        @ApiResponse(code = 400, message = "Failed to list files")
    })
    public ResponseEntity<List<String>> listFiles() {
        try {
            List<String> files = fileStorageService.listFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            logger.error("Error listing files: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
} 