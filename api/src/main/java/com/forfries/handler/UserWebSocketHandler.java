package com.forfries.handler;


import com.forfries.service.webscket.AdminWebSocketService;
import com.forfries.service.webscket.UserWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class UserWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserWebSocketService userWebSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userWebSocketService.addSession(session);
        log.info("[WebSocket]用户请求登录：{}" , session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("[WebSocket]用户发来消息：{}",payload);
        //TODO 目前不需要处理用户信息
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userWebSocketService.removeSession(session);
        log.info("[WebSocket]用户关闭连接：{}",session.getId());
    }
}