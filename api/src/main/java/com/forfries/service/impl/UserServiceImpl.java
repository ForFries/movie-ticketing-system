package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.entity.User;
import com.forfries.exception.AccountNotFoundException;
import com.forfries.exception.PasswordErrorException;
import com.forfries.properties.JwtProperties;
import com.forfries.service.UserService;
import com.forfries.mapper.UserMapper;
import com.forfries.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-10-29 13:57:38
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String login(String username, String password) {

        // 使用 MyBatis-Plus 的条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        // 调用 baseMapper 进行查询
        User user = this.baseMapper.selectOne(queryWrapper);
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        if(!user.getPassword().equals(password)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        String secretKey = user.getRole().equals(RoleConstant.ROLE_USER)?
                jwtProperties.getUserSecretKey():
                jwtProperties.getAdminSecretKey();

        long ttl = user.getRole().equals(RoleConstant.ROLE_USER)?
                jwtProperties.getUserTtl():
                jwtProperties.getAdminTtl();

        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        claims.put("cinemaId", user.getCinemaId());

        return JwtUtil.createJWT(secretKey,ttl,claims);
    }
}




