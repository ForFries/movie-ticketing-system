package com.forfries.common.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.dto.PageDTO;
import com.forfries.result.PageResult;
import com.forfries.common.service.PageableService;

public abstract class PageableServiceImpl<M extends BaseMapper<T>, T,D extends PageDTO>
        extends ServiceImpl<M, T>
        implements PageableService<T,D> {

    // 通用的分页查询方法
    public PageResult page(D pageDTO) {
        int currentPage = Integer.parseInt(pageDTO.getPage());
        int pageSize = Integer.parseInt(pageDTO.getPageSize());

        Page<T> page = new Page<>(currentPage, pageSize);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        String status = pageDTO.getStatus();
        //这里加了对status的
        if (status != null && !status.isEmpty()) {
            queryWrapper.like("status", status);
        }
        // 调用子类的方法来构建 QueryWrapper
        buildQueryWrapper(queryWrapper, pageDTO);

        IPage<T> resultPage = this.page(page, queryWrapper);
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    // 默认按照创建时间排序
    protected void buildQueryWrapper(QueryWrapper<T> queryWrapper, D pageDTO){
        queryWrapper.orderByDesc("updated_at");
    }
}