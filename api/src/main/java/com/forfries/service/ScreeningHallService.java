package com.forfries.service;

import com.forfries.common.service.PageableService;
import com.forfries.common.service.PageableWithCheckService;
import com.forfries.dto.ScreeningHallPageDTO;
import com.forfries.entity.ScreeningHall;

/**
* @author Nolan
* @description 针对表【screening_hall】的数据库操作Service
* @createDate 2024-10-30 08:01:15
*/
public interface ScreeningHallService extends PageableWithCheckService<ScreeningHall, ScreeningHallPageDTO> {

}
