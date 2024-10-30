package com.forfries.mapper;

import com.forfries.entity.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【ticket】的数据库操作Mapper
* @createDate 2024-10-30 15:37:54
* @Entity com.forfries.entity.Ticket
*/
@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {

}




