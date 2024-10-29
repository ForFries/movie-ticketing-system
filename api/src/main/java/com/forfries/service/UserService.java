package com.forfries.service;

import com.forfries.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Service
* @createDate 2024-10-29 13:57:38
*/
public interface UserService extends IService<User> {

    String login(String username, String password);
}
