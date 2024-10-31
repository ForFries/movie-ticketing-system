package com.forfries.common.service;

import com.forfries.dto.PageDTO;

public interface PageableWithCheckService<T,D extends PageDTO> extends PageableService<T,D> {
    T getByIdWithCheck(Long id);
    boolean deleteByIdWithCheck(Long id);
    boolean updateByIdWithCheck(Long id,T t);

    /*
     * check方法的作用时，传入id，调取当前Service查询id与CinemaId
     * 如果找到则返回check
     * 对于ScreeningHallService.check，传入ScreeningHallId，
     * 判断该ScreeningHallId是否属于该影院
     *
     * 对于ScheduleService.check，传入ScheduleId，
     * 判断该ScheduleId是否属于该影院
     */
    boolean check(Long id);
}

