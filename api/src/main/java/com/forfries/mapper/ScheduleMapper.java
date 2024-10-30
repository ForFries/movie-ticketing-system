package com.forfries.mapper;

import com.forfries.entity.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【schedule】的数据库操作Mapper
* @createDate 2024-10-30 15:37:42
* @Entity com.forfries.entity.Schedule
*/
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {

}




