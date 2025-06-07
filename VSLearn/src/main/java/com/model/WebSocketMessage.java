package com.model;

import lombok.Data;

@Data
public class WebSocketMessage {
    private String content;
    private String sender;
    private Long userId;
    private MessageType type;
    private String timestamp;

    public enum MessageType {
        CHAT,
        NOTIFICATION,
        SYSTEM
    }
} 