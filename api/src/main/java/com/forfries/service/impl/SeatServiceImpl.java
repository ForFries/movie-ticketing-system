package com.forfries.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.entity.Seat;
import com.forfries.service.SeatService;
import com.forfries.mapper.SeatMapper;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【seat】的数据库操作Service实现
* @createDate 2024-10-30 15:37:52
*/
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat>
    implements SeatService{

}




