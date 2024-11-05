package com.forfries.service.impl;

import com.forfries.service.WebSocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    private final Map<String, List<WebSocketSession>> cinemaAdminSessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) {
        String cinemaId = session.getAttributes().get("cinemaId").toString();
        cinemaAdminSessions.computeIfAbsent(cinemaId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    public void removeSession(WebSocketSession session) {
        String cinemaId = session.getAttributes().get("cinemaId").toString();
        List<WebSocketSession> sessions = cinemaAdminSessions.get(cinemaId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public void sendRefundNotification(String cinemaId, String ticketId) {
        List<WebSocketSession> sessions = cinemaAdminSessions.get(cinemaId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage("New refund request for ticket ID: " + ticketId));
                } catch (Exception e) {
                    System.err.println("Failed to send message to session: " + e.getMessage());
                }
            }
        }
    }
}
