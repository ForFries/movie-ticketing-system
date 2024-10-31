package com.forfries.service;

import com.forfries.common.service.PageableService;
import com.forfries.common.service.PageableWithCheckService;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.TicketOrder;
import com.forfries.entity.TicketOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Service
* @createDate 2024-10-30 15:37:57
*/
public interface TicketOrderService extends PageableService<TicketOrder, TicketOrderPageDTO> {

    boolean createTicketOrder(TicketOrderGenerationDTO ticketOrderGenerationDTO);
}
