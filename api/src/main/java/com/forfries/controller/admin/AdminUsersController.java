package com.forfries.controller.admin;

import com.forfries.constant.RoleConstant;
import com.forfries.dto.RegisterDTO;
import com.forfries.dto.UserPageDTO;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/users")
public class AdminUsersController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Result<?> allocateCinemaAdminAccount(@RequestParam Long cinemaId,
                                             @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO.getUsername(),
                registerDTO.getPassword(),
                RoleConstant.ROLE_CINEMA_ADMIN, cinemaId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCinemaAdminAccount(@PathVariable Long id){
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping
    public Result<PageResult> pageUsers(UserPageDTO userPageDTO) {

        PageResult pageResult = userService.page(userPageDTO);
        return Result.success(pageResult);
    }
}
