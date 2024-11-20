package com.forfries.service.webscket;

import org.springframework.web.socket.WebSocketSession;

public interface AdminWebSocketService {
    void addSession(WebSocketSession session);
    void removeSession(WebSocketSession session);
    void sendMessageToCinemaAdmin(String cinemaId , String Message);
    void sendRefundNotification(String cinemaId, String ticketId);

}
