package com.forfries.mapper;

import com.forfries.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-10-29 13:57:38
* @Entity com.forfries.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




