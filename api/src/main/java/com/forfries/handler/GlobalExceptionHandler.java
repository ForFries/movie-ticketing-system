package com.forfries.handler;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.forfries.exception.BaseException;
import com.forfries.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result baseExceptionHandler(BaseException ex){
        log.error("业务异常：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /**
     * 捕获所有异常
     * @param ex
     * @return
     */
//    @ExceptionHandler
//    public Result exceptionHandler(Exception ex){
//        log.error("通用异常：{}", ex.getMessage());
//        return Result.error(ex.getMessage());
//    }


    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
//    @ExceptionHandler
//    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
//        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
//        String message = ex.getMessage();
//        if(message.contains("Duplicate entry")){
//            String[] split = message.split(" ");
//            String username = split[2];
//            String msg = username + MessageConstant.ALREADY_EXISTS;
//            return Result.error(msg);
//        }else{
//            return Result.error(MessageConstant.UNKNOWN_ERROR);
//        }
//    }
}
