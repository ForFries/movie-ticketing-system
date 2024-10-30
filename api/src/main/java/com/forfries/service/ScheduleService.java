package com.forfries.service;

import com.forfries.common.service.PageableWithCheckService;
import com.forfries.dto.SchedulePageDTO;
import com.forfries.entity.Schedule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nolan
* @description 针对表【schedule】的数据库操作Service
* @createDate 2024-10-30 15:37:42
*/
public interface ScheduleService extends PageableWithCheckService<Schedule, SchedulePageDTO> {

}
