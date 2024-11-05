package com.forfries.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
@Service
public interface WebSocketService {
    void addSession(WebSocketSession session);
    void removeSession(WebSocketSession session);
    void sendRefundNotification(String cinemaId, String ticketId);
}
