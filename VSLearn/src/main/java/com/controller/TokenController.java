package com.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.security.PublicEndpoint;
import com.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/tokens")
@Api(tags = "tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @PublicEndpoint
    @GetMapping("/all")
    @ApiOperation(value = "Get all tokens from Redis")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Tokens retrieved successfully"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Map<String, Object>> getAllTokens() {
        try {
            Map<String, String> tokens = tokenService.getAllTokens();
            Map<String, Object> response = new HashMap<>();
            response.put("tokens", tokens);
            response.put("count", tokens.size());
            //sample log
            logger.debug("Debug message");
            logger.info("Info message");
            logger.warn("Warning message");
            logger.error("Error message");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve tokens: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PublicEndpoint
    @PostMapping("/store")
    @ApiOperation(value = "Store token in Redis")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Token stored successfully"),
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> storeToken(
            @ApiParam("Username") @RequestParam(required = true) String username,
            @ApiParam("Token") @RequestParam(required = true) String token) {
        try {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("Token cannot be empty");
            }
            
            tokenService.storeToken(username, token);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Token stored successfully");
            response.put("username", username);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to store token: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PublicEndpoint
    @GetMapping("/{username}")
    @ApiOperation(value = "Get token from Redis")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Token retrieved successfully"),
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 404, message = "Token not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> getToken(
            @ApiParam("Username") @PathVariable String username) {
        try {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            
            String token = tokenService.getToken(username);
            if (token == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Token not found for username: " + username);
                return ResponseEntity.status(404).body(error);
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", username);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to retrieve token: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
} 