package com.forfries.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.Impl.PageableServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.RoleConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.dto.UserPageDTO;
import com.forfries.entity.User;
import com.forfries.exception.AccountNotFoundException;
import com.forfries.exception.PasswordErrorException;
import com.forfries.exception.SystemException;
import com.forfries.exception.UsernameRepeatException;
import com.forfries.properties.JwtProperties;
import com.forfries.service.common.UserService;
import com.forfries.mapper.UserMapper;
import com.forfries.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-10-29 13:57:38
*/
@Service
public class UserServiceImpl extends PageableServiceImpl<UserMapper,User,UserPageDTO>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void buildQueryWrapper(QueryWrapper<User> queryWrapper, UserPageDTO userPageDTO) {
        if(userPageDTO.getCinemaId()!=null){
            queryWrapper.eq("cinema_id", userPageDTO.getCinemaId());
        }
        String role = userPageDTO.getRole();

        if(role !=null && !role.isEmpty()){
            queryWrapper.in("role", userPageDTO.getRole());
        }
        queryWrapper.orderByDesc("updated_at");
    }


    @Override
    public String createToken(String username, String password) {

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
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("role", user.getRole());
        claims.put("cinemaId", String.valueOf(user.getCinemaId()));

        return JwtUtil.createJWT(secretKey,ttl,claims);
    }

    @Override
    public String createTokenWithRegister(String openId) {
        // 使用 MyBatis-Plus 的条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);

        // 调用 baseMapper 进行查询
        User user = this.baseMapper.selectOne(queryWrapper);
        if(user == null){
            user = register(openId,RoleConstant.ROLE_USER);
        }

        String secretKey = user.getRole().equals(RoleConstant.ROLE_USER)?
                jwtProperties.getUserSecretKey():
                jwtProperties.getAdminSecretKey();

        long ttl = user.getRole().equals(RoleConstant.ROLE_USER)?
                jwtProperties.getUserTtl():
                jwtProperties.getAdminTtl();

        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("role", user.getRole());
        claims.put("cinemaId", String.valueOf(user.getCinemaId()));

        return JwtUtil.createJWT(secretKey,ttl,claims);
    }

    @Override
    public User register(String openId, String role) {
        return register(openId,openId,role,null,openId);
    }

    @Override
    public User register(String username, String password, String role) {
        return register(username, password, role,null,null);
    }

    @Override
    public User register(String username, String password,String role,Long cinemaId,String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if(this.baseMapper.selectOne(queryWrapper)!=null){
            throw new UsernameRepeatException(MessageConstant.USERNAME_REPEAT);
        }

        User user = User.builder()
                    .openId(openId)
                    .username(username)
                    .password(password)
                    .role(role)
                    .status(StatusConstant.NORMAL)
                    .cinemaId(cinemaId)
                    .build();

        //TODO 对密码进行加密
        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 保存到数据库
        this.save(user);
        return user;
    }
}




