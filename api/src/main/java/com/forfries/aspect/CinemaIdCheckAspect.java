package com.forfries.aspect;


import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.context.BaseContext;

import com.forfries.exception.PermissionErrorException;
import com.forfries.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class CinemaIdCheckAspect {

    @Around("@annotation(com.forfries.annotation.CinemaIdCheck)")
    public Object checkCinemaPermission(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("进入CinemaIdCheck_AOP");

        Map<String, String> payload = BaseContext.getCurrentPayload();

        // 获取当前管理员的 cinemaId
        Long adminCinemaId = Long.parseLong(payload.get("cinemaId"));

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Long id = null;

        // 检查参数中是否有 Long 类型的 id
        for (Object arg : args) {
            if (arg instanceof Long) {
                id = (Long) arg;
                break;
            }
        }

        if (id == null) {
            throw new SystemException(MessageConstant.SYSTEM_ERROR_ILLEGAL_ARGUMENT);
        }

        // 获取目标对象和方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();

        // 获取 Mapper
        BaseMapper<Object> mapper = (BaseMapper<Object>) getMapper(target);

        // 检查权限
        if (!checkCinemaId(mapper, id, adminCinemaId)) {
            throw new PermissionErrorException(MessageConstant.PERMISSION_ERROR_CINEMA);
        }

        // 继续执行原方法
        return joinPoint.proceed();
    }

    private boolean checkCinemaId(BaseMapper<Object> mapper, Long id, Long adminCinemaId) {
        // 使用 Mapper 进行查询
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("cinema_id", adminCinemaId);
        return mapper.selectCount(queryWrapper) > 0;
    }

    private BaseMapper<?> getMapper(Object target) throws SystemException {
        // 反射获取 Mapper
        try {
            return ((IService<?>) target).getBaseMapper();
        } catch (Exception e) {
            log.error("报错：{}",e.getMessage());
            throw new SystemException(MessageConstant.SYSTEM_ERROR_NO_MAPPER);
        }
    }
}