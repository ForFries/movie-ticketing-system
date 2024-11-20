package com.forfries.config;
import com.forfries.handler.AdminWebSocketHandler;
import com.forfries.handler.UserWebSocketHandler;
import com.forfries.interceptor.JwtHandshakeInterceptor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;
    @Autowired
    private AdminWebSocketHandler adminWebSocketHandler;
    @Autowired
    private UserWebSocketHandler userWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //添加管理员ws配置
        registry.addHandler(adminWebSocketHandler, "/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");

        //添加用户登录ws配置
        registry.addHandler(userWebSocketHandler, "/login")
                .setAllowedOrigins("*");
    }
}