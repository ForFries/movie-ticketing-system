package com.forfries.common.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.forfries.annotation.CinemaIdCheck;
import com.forfries.dto.PageDTO;
import com.forfries.common.PageableService;

public abstract class PageableWithCheckServiceImpl<M extends BaseMapper<T>, T,D extends PageDTO>
        extends PageableServiceImpl<M, T, D>
        implements PageableService<T,D> {

    @CinemaIdCheck
   public T getByIdWithCheck(Long id){
       return getById(id);
   }
    @CinemaIdCheck
   public boolean deleteByIdWithCheck(Long id){
       return removeById(id);
   }
    @CinemaIdCheck
   public boolean updateByIdWithCheck(Long id,T t){
       return updateById(t);
   }
    @CinemaIdCheck
    public boolean check(Long id){
        return true;
   }
}
