package com.forfries.service.webscket;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.socket.WebSocketSession;

public interface UserWebSocketService {
    void addSession(WebSocketSession session) throws WxErrorException, JsonProcessingException;
    void removeSession(WebSocketSession session);
    void sendMessageToUser(String sceneId , String Message);
    void sendJwtToUser(String sceneId, String JwtToken) throws JsonProcessingException;
    void sendQrCodeToUser(String sceneId, String QrcodeUrl) throws JsonProcessingException;


}
