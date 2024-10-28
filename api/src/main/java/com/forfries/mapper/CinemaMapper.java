package com.forfries.mapper;

import com.forfries.entity.Cinema;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【cinema】的数据库操作Mapper
* @createDate 2024-10-28 22:17:09
* @Entity com.forfries.entity.Cinema
*/
@Mapper
public interface CinemaMapper extends BaseMapper<Cinema> {

}




