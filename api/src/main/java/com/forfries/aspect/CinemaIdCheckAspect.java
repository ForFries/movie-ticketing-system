package com.forfries.aspect;


import com.auth0.jwt.interfaces.Claim;
import com.forfries.context.BaseContext;
import com.forfries.exception.PermissionErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.naming.Context;
import java.util.Map;

@Aspect
@Component
public class CinemaIdCheckAspect {

    @Before("@annotation(com.forfries.annotation.CinemaIdCheck)")
    public void checkUserId(JoinPoint joinPoint) throws PermissionErrorException {
        Map<String, Claim> currentClaims = BaseContext.getCurrentClaims();
        Claim claim = currentClaims.get("role");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String requestedUserId = request.getParameter("id");

    }
}