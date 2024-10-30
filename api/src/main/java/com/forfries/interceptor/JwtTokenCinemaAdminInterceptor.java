package com.forfries.interceptor;


import com.auth0.jwt.interfaces.Claim;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.context.BaseContext;
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

import java.util.Map;


/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenCinemaAdminInterceptor implements HandlerInterceptor {

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        log.info("token : {}", token);
        try {
            Map<String, Claim> payload = JwtUtil.decodeJWT(
                    jwtProperties.getAdminSecretKey(),
                    token);
            log.info("token : {}", payload);
            BaseContext.setCurrentClaims(payload);
            if(payload.get("role").asString().equals(RoleConstant.ROLE_SYSTEM_ADMIN)){
                return true;
            }
            //TODO 这里要把异常全部通过全局异常处理器
            String cinemaId = request.getParameter("cinemaId");
            if(!cinemaId.equals(payload.get("cinemaId").asString()))
                throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR);
            return true;
        }catch (Exception e)
        {
            response.setStatus(401);
            return false;
        }

    }
}
