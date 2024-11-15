package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.forfries.common.Impl.PageableWithCheckServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.dto.SchedulePageDTO;

import com.forfries.entity.Schedule;

import com.forfries.exception.TimeConflictException;
import com.forfries.mapper.ScheduleMapper;

import com.forfries.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        if (movieId != null) {
            queryWrapper.eq("movie_id", movieId);
        }

        Long screeningHallId = schedulePageDTO.getScreeningHallId();
        if (screeningHallId != null) {
            queryWrapper.eq("screening_hall_id", screeningHallId);
        }

        queryWrapper.eq("cinema_id", schedulePageDTO.getCinemaId());

        LocalDate date = schedulePageDTO.getDate();
        if (date != null) {
            // 当天的开始时间和结束时间
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59, 999_999_999);

            // 创建一个新的查询条件组，用于时间过滤
            queryWrapper.and(wrapper -> wrapper
                    .between("start_time", startOfDay, endOfDay)
                    .or()
                    .between("end_time", startOfDay, endOfDay)
                    .or()
                    .le("start_time", startOfDay)
                    .ge("end_time", endOfDay)
            );
        }


        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }

    @Override
    public boolean addWithoutConflict(Schedule schedule) {
        if(checkTimeConflict(schedule)) {
            throw new TimeConflictException(MessageConstant.TIME_CONFLICT);
        }
        return save(schedule);
    }

    @Override
    public boolean updateByIdWithCheckWithoutConflict(Long id, Schedule schedule) {
        if(checkTimeConflict(schedule)) {
            throw new TimeConflictException(MessageConstant.TIME_CONFLICT);
        }
        return updateByIdWithCheck(id, schedule);
    }

    private boolean checkTimeConflict(Schedule newSchedule) {
        LocalDateTime newStartTime = newSchedule.getStartTime();
        LocalDateTime newEndTime = newSchedule.getEndTime();

        if(newStartTime.isAfter(newEndTime)) {
            throw new TimeConflictException(MessageConstant.TIME_CONFLICT_TIME);
        }
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cinema_id", newSchedule.getCinemaId())
                .eq("movie_id", newSchedule.getMovieId())
                .eq("screening_hall_id", newSchedule.getScreeningHallId())
                .and(wrapper -> wrapper.between("start_time", newStartTime, newEndTime)
                        .or()
                        .between("end_time", newStartTime, newEndTime)
                        .or()
                        .le("start_time", newStartTime).ge("end_time", newEndTime));
        if (newSchedule.getId() != null) {
            queryWrapper.ne("id", newSchedule.getId());
        }
        List<Schedule> conflictingSchedules = list(queryWrapper);

        return !conflictingSchedules.isEmpty();
    }
}




