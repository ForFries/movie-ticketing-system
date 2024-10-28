package com.forfries.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.forfries.entity.User;

import com.forfries.mapper.UserMapper;
import com.forfries.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-10-28 21:01:17
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




