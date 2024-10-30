package com.forfries.service;

import com.forfries.dto.CinemaPageDTO;
import com.forfries.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.result.PageResult;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Service
* @createDate 2024-10-29 13:57:38
*/
public interface UserService extends IService<User> {

    String createToken(String username, String password);


    User register(String username, String password,String role);

    User register(String username, String password,String role,Long cinemaId);
}
