package com.forfries.service;

import com.forfries.common.service.PageableService;
import com.forfries.dto.CinemaPageDTO;
import com.forfries.entity.Cinema;

/**
* @author Nolan
* @description 针对表【cinema】的数据库操作Service
* @createDate 2024-10-28 22:17:09
*/
public interface CinemaService extends PageableService<Cinema,CinemaPageDTO> {

}
