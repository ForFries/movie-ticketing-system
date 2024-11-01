package com.forfries.common.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.dto.PageDTO;
import com.forfries.result.PageResult;
import com.forfries.common.PageableService;

import java.util.Arrays;

public abstract class PageableServiceImpl<M extends BaseMapper<T>, T,D extends PageDTO>
        extends ServiceImpl<M, T>
        implements PageableService<T,D> {

    // 通用的分页查询方法
    public PageResult page(D pageDTO) {
        int currentPage = Integer.parseInt(pageDTO.getPage());
        int pageSize = Integer.parseInt(pageDTO.getPageSize());

        Page<T> page = new Page<>(currentPage, pageSize);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        //这里加了对status的更新，支持多个status
        String allStatus = pageDTO.getStatus();
        if(allStatus != null && !allStatus.isEmpty()){
            String[] statusArray = allStatus.split(",");
            queryWrapper.in("status", Arrays.asList(statusArray));
        }

        // 调用子类的方法来构建 QueryWrapper
        //TODO 这里还需要根据不同的情况来进行排序 例如用户侧按时间排序之类的
        buildQueryWrapper(queryWrapper, pageDTO);

        IPage<T> resultPage = this.page(page, queryWrapper);
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    // 默认按照创建时间排序
    protected void buildQueryWrapper(QueryWrapper<T> queryWrapper, D pageDTO){
        queryWrapper.orderByDesc("updated_at");
    }
}