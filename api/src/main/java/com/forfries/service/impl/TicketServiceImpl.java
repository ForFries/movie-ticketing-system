package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.entity.Ticket;
import com.forfries.result.Result;
import com.forfries.service.TicketService;
import com.forfries.mapper.TicketMapper;
import com.forfries.vo.ScheduleSeatVO;
import org.springframework.stereotype.Service;

/**
* @author Nolan
* @description 针对表【ticket】的数据库操作Service实现
* @createDate 2024-10-30 15:37:54
*/
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>
    implements TicketService{


}




