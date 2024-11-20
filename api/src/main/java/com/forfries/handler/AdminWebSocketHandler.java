package com.forfries.handler;


import com.forfries.service.webscket.AdminWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class AdminWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private AdminWebSocketService adminWebSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        adminWebSocketService.addSession(session);
        log.info("[WebSocket]新的连接已经建立：{}" , session.getId());
        session.sendMessage(new TextMessage("[WebSocket]新的连接已经建立：" + session.getId()));
        session.sendMessage(new TextMessage("[WebSocket]cinemaId:" + session.getAttributes().get("cinemaId").toString()));
        session.sendMessage(new TextMessage("[WebSocket]userId:" + session.getAttributes().get("userId").toString()));
        session.sendMessage(new TextMessage("[WebSocket]role:" + session.getAttributes().get("role").toString()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("[WebSocket]发来消息：{}",payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        adminWebSocketService.removeSession(session);
        log.info("[WebSocket]关闭连接：{}",session.getId());
    }
}