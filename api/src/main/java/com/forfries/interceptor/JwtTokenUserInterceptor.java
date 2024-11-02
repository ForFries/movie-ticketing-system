package com.forfries.interceptor;


import com.auth0.jwt.interfaces.Claim;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.context.BaseContext;
import com.forfries.exception.JwtErrorException;
import com.forfries.exception.PermissionErrorException;
import com.forfries.properties.JwtProperties;
import com.forfries.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;


/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws JwtErrorException, PermissionErrorException {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        log.info("token : {}", token);
        Map<String, Claim> payload = JwtUtil.decodeJWT(
                jwtProperties.getUserSecretKey(),
                token);
        log.info("token : {}", payload);

        Map<String,String> map = new HashMap<>();
        String payloadRole = payload.get("role").asString();
        String payloadUserId = payload.get("userId").asString();

        //理论上这里并不会报这个错误
        if(!payloadRole.equals(RoleConstant.ROLE_USER))
            throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR_USER);

        map.put("role",payloadRole);
        map.put("userId",payloadUserId);
        map.put("cinemaId","");

        BaseContext.setCurrentPayload(map);

        return true;

    }
}
