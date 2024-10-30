package com.forfries.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.service.Impl.PageableServiceImpl;
import com.forfries.common.service.Impl.PageableWithCheckServiceImpl;
import com.forfries.dto.ScreeningHallPageDTO;
import com.forfries.entity.ScreeningHall;
import com.forfries.mapper.ScreeningHallMapper;
import com.forfries.service.ScreeningHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【screening_hall】的数据库操作Service实现
* @createDate 2024-10-30 08:01:15
*/
@Service
public class ScreeningHallServiceImpl extends PageableWithCheckServiceImpl<ScreeningHallMapper, ScreeningHall,ScreeningHallPageDTO>
        implements ScreeningHallService {
    @Autowired
    private ScreeningHallMapper screeningHallMapper;

    @Override
    protected void buildQueryWrapper(QueryWrapper<ScreeningHall> queryWrapper, ScreeningHallPageDTO screeningHallPageDTO) {
        String name = screeningHallPageDTO.getName();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        queryWrapper.eq("cinema_id", screeningHallPageDTO.getCinemaId());
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }
}




