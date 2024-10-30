package com.forfries.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.entity.Movie;
import com.forfries.service.MovieService;
import com.forfries.mapper.MovieMapper;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【movie】的数据库操作Service实现
* @createDate 2024-10-30 08:02:52
*/
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie>
    implements MovieService{

}




