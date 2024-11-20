package com.forfries.service.webscket.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forfries.constant.MessageConstant;
import com.forfries.exception.WebSocketException;
import com.forfries.service.common.WechatService;
import com.forfries.service.webscket.UserWebSocketService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@Service
public class UserWebSocketServiceImpl implements UserWebSocketService {

    @Autowired
    private WechatService wechatService;

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session) throws WxErrorException, JsonProcessingException {
        //TODO 这里理论上需要实现sceneId过期，但是目前，如果需要的话可以重新连接Websocket
        String sceneId = wechatService.getSceneId();
        userSessions.put(sceneId, session);
        sendQrCodeToUser(sceneId,wechatService.getQrcodeUrl(sceneId));
    }

    public void removeSession(WebSocketSession session) {
        userSessions.values().remove(session);
    }

    public void sendJwtToUser(String sceneId, String jwtToken) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "jwt");
        message.put("data", jwtToken);

        sendMessageToUser(sceneId,new ObjectMapper().writeValueAsString(message));
    }
    public void sendQrCodeToUser(String sceneId, String QrcodeUrl) throws JsonProcessingException {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "qrcode");
        message.put("data", QrcodeUrl);

        sendMessageToUser(sceneId,new ObjectMapper().writeValueAsString(message));
    }

    public void sendMessageToUser(String sceneId , String Message) {
        WebSocketSession session = userSessions.get(sceneId);
        if(session == null){
            throw new WebSocketException(MessageConstant.WEBSOCKET_NULL);
        }

        try {
            session.sendMessage(new TextMessage(Message));
        } catch (Exception e) {
            throw new WebSocketException(MessageConstant.WEBSOCKET_ERROR);
        }

    }

}
