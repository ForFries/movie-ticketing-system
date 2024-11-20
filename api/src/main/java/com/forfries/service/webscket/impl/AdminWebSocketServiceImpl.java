package com.forfries.service.webscket.impl;

import com.forfries.constant.MessageConstant;
import com.forfries.exception.WebSocketException;
import com.forfries.service.webscket.AdminWebSocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


//目前仅支持CinemaAdmin、Admin的消息发送
@Service
public class AdminWebSocketServiceImpl implements AdminWebSocketService {
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
        sendMessageToCinemaAdmin(cinemaId,"New refund request for ticket ID: " + ticketId);
    }

    public void sendMessageToCinemaAdmin(String cinemaId , String Message) {
        List<WebSocketSession> sessions = cinemaAdminSessions.get(cinemaId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(Message));
                } catch (Exception e) {
                    throw new WebSocketException(MessageConstant.WEBSOCKET_ERROR);
                }
            }
        }
    }
}
