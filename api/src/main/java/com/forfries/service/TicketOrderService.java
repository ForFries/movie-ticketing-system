package com.forfries.service;

import com.forfries.common.PageableService;
import com.forfries.common.PageableWithCheckService;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.TicketOrder;
import com.forfries.result.Result;
import com.forfries.vo.ScheduleSeatVO;

import java.util.List;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Service
* @createDate 2024-10-30 15:37:57
*/
public interface TicketOrderService extends PageableWithCheckService<TicketOrder, TicketOrderPageDTO> {

    boolean createTicketOrder(TicketOrderGenerationDTO ticketOrderGenerationDTO);

    boolean checkSeatOccupied(List<Long> seatIds,Long scheduleId);

    ScheduleSeatVO getScheduleSeats(Long scheduleId);

    boolean cancelTicketOrder(long id);
}
