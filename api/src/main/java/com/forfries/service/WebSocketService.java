package com.forfries.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketService {
    void addSession(WebSocketSession session);
    void removeSession(WebSocketSession session);
    void sendMessageToCinemaAdmin(String cinemaId , String Message);
    void sendRefundNotification(String cinemaId, String ticketId);

}
