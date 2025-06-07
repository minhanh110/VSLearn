package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseService {

    @Autowired
    private DataSource dataSource;

    public boolean checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(5); // Check if connection is valid within 5 seconds
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDatabaseInfo() {
        try (Connection connection = dataSource.getConnection()) {
            return String.format("Database connected successfully!\n" +
                    "Database Product: %s\n" +
                    "Database Version: %s\n" +
                    "Driver Name: %s\n" +
                    "Driver Version: %s",
                    connection.getMetaData().getDatabaseProductName(),
                    connection.getMetaData().getDatabaseProductVersion(),
                    connection.getMetaData().getDriverName(),
                    connection.getMetaData().getDriverVersion());
        } catch (SQLException e) {
            return "Failed to get database info: " + e.getMessage();
        }
    }
} 