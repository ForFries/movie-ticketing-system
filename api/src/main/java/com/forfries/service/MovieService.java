package com.forfries.service;

import com.forfries.common.PageableService;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Movie;

/**
* @author Nolan
* @description 针对表【movie】的数据库操作Service
* @createDate 2024-10-30 08:02:53
*/
public interface MovieService extends PageableService<Movie,MoviePageDTO> {
}
