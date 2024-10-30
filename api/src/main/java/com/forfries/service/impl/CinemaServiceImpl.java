package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.dto.CinemaPageDTO;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Cinema;
import com.forfries.entity.Movie;
import com.forfries.mapper.MovieMapper;
import com.forfries.result.PageResult;
import com.forfries.service.CinemaService;
import com.forfries.mapper.CinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【cinema】的数据库操作Service实现
* @createDate 2024-10-28 22:17:09
*/
@Service
public class CinemaServiceImpl extends PageableServiceImpl<CinemaMapper, Cinema,CinemaPageDTO>
    implements CinemaService{

    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    protected void buildQueryWrapper(QueryWrapper<Cinema> queryWrapper, CinemaPageDTO cinemaPageDTO) {
        if (cinemaPageDTO.getName() != null && !cinemaPageDTO.getName().isEmpty()) {
            queryWrapper.like("title", cinemaPageDTO.getName());
        }
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }
}




