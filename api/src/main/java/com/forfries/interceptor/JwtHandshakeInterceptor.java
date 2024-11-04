package com.forfries.interceptor;


import com.auth0.jwt.interfaces.Claim;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.context.BaseContext;
import com.forfries.exception.PermissionErrorException;
import com.forfries.properties.JwtProperties;
import com.forfries.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverRequest, ServerHttpResponse serverResponse, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //1、从请求头中获取令牌
        HttpServletRequest request = ((ServletServerHttpRequest) serverRequest).getServletRequest();
        try {
            String token = request.getHeader(jwtProperties.getAdminTokenName());
            log.info("token : {}", token);
            Map<String, Claim> payload = JwtUtil.decodeJWT(
                    jwtProperties.getAdminSecretKey(),
                    token);
            log.info("token : {}", payload);

            Map<String,String> map = new HashMap<>();
            String payloadRole = payload.get("role").asString();
            String payloadUserId = payload.get("userId").asString();
            String payloadCinemaId = payload.get("cinemaId").asString();
            String cinemaId = request.getParameter("cinemaId");

            if(payloadRole.equals(RoleConstant.ROLE_USER))
                throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR);

            if(cinemaId == null || cinemaId.isEmpty())
                throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR_NULL);

            if(payloadRole.equals(RoleConstant.ROLE_CINEMA_ADMIN)
                    &&!payloadCinemaId.equals(cinemaId))
                throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR_ID);

            map.put("role",payloadRole);
            map.put("userId",payloadUserId);
            map.put("cinemaId",cinemaId);

            BaseContext.setCurrentPayload(map);
            return true;
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            serverResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            serverResponse.getBody().write(("Error: " + e.getMessage()).getBytes());
            return false;
        }

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 可选的后握手处理
    }
}