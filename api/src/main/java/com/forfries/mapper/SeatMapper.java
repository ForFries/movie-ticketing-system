package com.forfries.mapper;

import com.forfries.entity.Seat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【seat】的数据库操作Mapper
* @createDate 2024-10-30 15:37:52
* @Entity com.forfries.entity.Seat
*/
@Mapper
public interface SeatMapper extends BaseMapper<Seat> {

}




