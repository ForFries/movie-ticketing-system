package com.forfries.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.forfries.dto.PageDTO;
import com.forfries.result.PageResult;

public interface PageableService<T,D extends PageDTO> extends IService<T> {
    PageResult page(D pageDTO);
}

