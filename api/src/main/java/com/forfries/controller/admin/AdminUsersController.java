package com.forfries.controller.admin;

import com.forfries.result.Result;
import com.forfries.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/users")
public class AdminUsersController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Result allocateCinemaAdminAccount(@RequestParam String username,
                                             @RequestParam String password){
        return Result.success();
    }


}
