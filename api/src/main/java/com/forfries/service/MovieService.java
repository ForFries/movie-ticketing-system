package com.forfries.service;

import com.forfries.dto.CinemaPageDTO;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.result.PageResult;

/**
* @author Nolan
* @description 针对表【movie】的数据库操作Service
* @createDate 2024-10-30 08:02:53
*/
public interface MovieService extends IService<Movie> {
    PageResult page(MoviePageDTO moviePageDTO);

}
