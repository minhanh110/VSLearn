package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.model.WebSocketMessage;
import com.service.WebSocketService;
import com.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/ws")
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send/user/{userId}")
    public ResponseEntity<?> sendToUser(
            @PathVariable Long userId,
            @RequestBody WebSocketMessage message,
            @RequestHeader("Authorization") String token) {
        
        // Extract user ID from JWT token
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        Long senderId = jwtTokenProvider.getUserId(jwtToken);
        
        message.setSender(senderId.toString());
        message.setUserId(userId);
        webSocketService.sendToUser(userId, message);
        
        return ResponseEntity.ok().body("Message sent to user: " + userId);
    }

    @MessageMapping("/send-private")
    public void sendPrivateMessage(@Payload WebSocketMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String token = headerAccessor.getFirstNativeHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            Long senderId = jwtTokenProvider.getUserId(jwtToken);
            message.setSender(senderId.toString());
            webSocketService.sendToUser(message.getUserId(), message);
        }
    }

    // Test endpoint to verify WebSocket connection
    @GetMapping("/test")
    public ResponseEntity<?> testWebSocket() {
        WebSocketMessage message = new WebSocketMessage();
        message.setContent("Test message");
        message.setType(WebSocketMessage.MessageType.SYSTEM);
        messagingTemplate.convertAndSend("/topic/public", message);
        return ResponseEntity.ok().body("Test message sent");
    }
} 