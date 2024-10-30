package com.forfries.mapper;

import com.forfries.entity.TicketOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Mapper
* @createDate 2024-10-30 15:37:57
* @Entity com.forfries.entity.TicketOrder
*/
@Mapper
public interface TicketOrderMapper extends BaseMapper<TicketOrder> {

}




