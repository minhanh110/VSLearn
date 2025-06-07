package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.model.WebSocketMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendToGroup(String groupId, WebSocketMessage message) {
        messagingTemplate.convertAndSend("/topic/group/" + groupId, message);
    }

    public void sendToUser(Long userId, WebSocketMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        messagingTemplate.convertAndSendToUser(
            userId.toString(),
            "/queue/messages",
            message
        );
    }

    public void sendToAll(WebSocketMessage message) {
        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        messagingTemplate.convertAndSend("/topic/public", message);
    }
} 