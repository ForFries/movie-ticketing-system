package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.dto.CinemaPageDTO;
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
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema>
    implements CinemaService{

    @Autowired
    private CinemaMapper cinemaMapper;

    public PageResult page(CinemaPageDTO cinemaPageDTO) {
        // 创建 Page 对象，传入当前页码和每页显示的记录数
        int currentPage = Integer.parseInt(cinemaPageDTO.getPage());
        int pageSize = Integer.parseInt(cinemaPageDTO.getPageSize());

        IPage<Cinema> page = new Page<>(currentPage, pageSize);

        // 创建 QueryWrapper 对象，用于构建查询条件
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();

        // 添加模糊查询条件
        if (cinemaPageDTO.getName() != null && !cinemaPageDTO.getName().isEmpty()) {
            queryWrapper.like("name", cinemaPageDTO.getName());
        }

        IPage<Cinema> resultPage = this.page(page, queryWrapper);
        // 调用基础服务的方法进行分页查询
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }
}




