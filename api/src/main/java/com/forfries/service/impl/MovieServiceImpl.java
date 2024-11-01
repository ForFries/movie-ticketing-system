package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.Impl.PageableServiceImpl;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Movie;
import com.forfries.service.MovieService;
import com.forfries.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【movie】的数据库操作Service实现
* @createDate 2024-10-30 08:02:52
*/
@Service
public class MovieServiceImpl extends PageableServiceImpl<MovieMapper, Movie,MoviePageDTO>
    implements MovieService{
    @Autowired
    private MovieMapper movieMapper;

    @Override
    protected void buildQueryWrapper(QueryWrapper<Movie> queryWrapper, MoviePageDTO moviePageDTO) {
        if (moviePageDTO.getTitle() != null && !moviePageDTO.getTitle().isEmpty()) {
            queryWrapper.like("title", moviePageDTO.getTitle());
        }
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }
}




