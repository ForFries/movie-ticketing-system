package com.forfries.controller.common;

import com.forfries.result.Result;
import com.forfries.service.UserService;
import com.forfries.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping("/api/login")
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password){

        return Result.success(userService.login(username,password));

    }
}
