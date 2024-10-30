package com.forfries.common.service;

import com.forfries.dto.PageDTO;

public interface PageableWithCheckService<T,D extends PageDTO> extends PageableService<T,D> {
    T getByIdWithCheck(Long id);
    boolean deleteByIdWithCheck(Long id);
    boolean updateByIdWithCheck(Long id,T t);
}

