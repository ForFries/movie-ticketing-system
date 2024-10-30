package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.dto.CinemaPageDTO;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Cinema;
import com.forfries.entity.Movie;
import com.forfries.result.PageResult;
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
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie>
    implements MovieService{
    @Autowired
    private MovieMapper movieMapper;

    public PageResult page(MoviePageDTO moviePageDTO) {
        // 创建 Page 对象，传入当前页码和每页显示的记录数
        int currentPage = Integer.parseInt(moviePageDTO.getPage());
        int pageSize = Integer.parseInt(moviePageDTO.getPageSize());

        IPage<Movie> page = new Page<>(currentPage, pageSize);

        // 创建 QueryWrapper 对象，用于构建查询条件
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();

        // 添加模糊查询条件
        if (moviePageDTO.getTitle() != null && !moviePageDTO.getTitle().isEmpty()) {
            queryWrapper.like("title", moviePageDTO.getTitle());
        }

        IPage<Movie> resultPage = this.page(page, queryWrapper);
        // 调用基础服务的方法进行分页查询
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }
}




