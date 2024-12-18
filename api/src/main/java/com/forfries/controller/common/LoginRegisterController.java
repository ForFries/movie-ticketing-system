package com.forfries.controller.common;

import com.forfries.constant.RoleConstant;
import com.forfries.result.Result;
import com.forfries.service.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class LoginRegisterController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password){

        return Result.success(userService.createToken(username,password));

    }

    @PostMapping("/register")
    public Result<?> register(@RequestParam String username,
                              @RequestParam String password){
        userService.register(username,password, RoleConstant.ROLE_USER);
        return Result.success();
    }
}
