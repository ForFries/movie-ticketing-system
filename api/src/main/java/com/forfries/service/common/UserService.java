package com.forfries.service.common;

import com.forfries.dto.UserPageDTO;
import com.forfries.entity.User;
import com.forfries.common.PageableService;
/**
* @author Nolan
* @description 针对表【user】的数据库操作Service
* @createDate 2024-10-29 13:57:38
*/
public interface UserService extends PageableService<User, UserPageDTO> {

    String createToken(String username, String password);

    User register(String username, String password,String role);

    User register(String username, String password,String role,Long cinemaId);
}
