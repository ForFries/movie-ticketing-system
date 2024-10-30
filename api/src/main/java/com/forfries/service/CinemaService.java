package com.forfries.service;

import com.forfries.dto.CinemaPageDTO;
import com.forfries.entity.Cinema;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.result.PageResult;

/**
* @author Nolan
* @description 针对表【cinema】的数据库操作Service
* @createDate 2024-10-28 22:17:09
*/
public interface CinemaService extends IService<Cinema> {
    PageResult page(CinemaPageDTO cinemaPageDTO);
}
