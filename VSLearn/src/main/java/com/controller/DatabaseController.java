package com.controller;

import com.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/check")
    public ResponseEntity<?> checkDatabaseConnection() {
        boolean isConnected = databaseService.checkDatabaseConnection();
        if (isConnected) {
            return ResponseEntity.ok(databaseService.getDatabaseInfo());
        } else {
            return ResponseEntity.badRequest().body("Failed to connect to database");
        }
    }
} 