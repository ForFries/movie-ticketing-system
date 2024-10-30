package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.forfries.common.service.Impl.PageableWithCheckServiceImpl;
import com.forfries.dto.SchedulePageDTO;

import com.forfries.entity.Schedule;

import com.forfries.mapper.ScheduleMapper;

import com.forfries.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【schedule】的数据库操作Service实现
* @createDate 2024-10-30 15:37:42
*/
@Service
public class ScheduleServiceImpl extends PageableWithCheckServiceImpl<ScheduleMapper, Schedule, SchedulePageDTO>
        implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    protected void buildQueryWrapper(QueryWrapper<Schedule> queryWrapper, SchedulePageDTO schedulePageDTO) {
        Long movieId = schedulePageDTO.getMovieId();
        if (movieId != null ) {
            queryWrapper.eq("movie_id", movieId);
        }
        Long screeningHallId = schedulePageDTO.getScreeningHallId();
        if (movieId != null ) {
            queryWrapper.eq("screening_hall_id", screeningHallId);
        }
        //TODO 这里还有日期
        queryWrapper.eq("cinema_id", schedulePageDTO.getCinemaId());
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }
}




