package com.forfries.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forfries.common.service.Impl.PageableServiceImpl;
import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.context.BaseContext;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.Ticket;
import com.forfries.entity.TicketOrder;
import com.forfries.exception.SystemException;
import com.forfries.mapper.TicketOrderMapper;
import com.forfries.service.ScheduleService;
import com.forfries.service.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
* @author Nolan
* @description 针对表【ticket_order】的数据库操作Service实现
* @createDate 2024-10-30 15:37:57
*/
@Service
public class TicketOrderServiceImpl extends PageableServiceImpl<TicketOrderMapper, TicketOrder, TicketOrderPageDTO>
        implements TicketOrderService {
    @Autowired
    private TicketOrderMapper ticketOrderMapper;

    @Autowired
    private ScheduleService scheduleService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private static final Random random = new Random();

    public static String generateOrderNumber() {
        // 获取当前时间戳
        String timestamp = LocalDateTime.now().format(formatter);
        // 生成一个4位随机数
        int randomNum = 1000 + random.nextInt(9000);
        return timestamp + randomNum;
    }
    @Override
    protected void buildQueryWrapper(QueryWrapper<TicketOrder> queryWrapper, TicketOrderPageDTO ticketOrderPageDTO) {
        String orderNum = ticketOrderPageDTO.getOrderNum();
        if (orderNum != null && !orderNum.isEmpty()) {
            queryWrapper.like("orderNum", orderNum);
        }
        Long scheduleId = ticketOrderPageDTO.getScheduleId();
        if (scheduleId != null ) {
            queryWrapper.eq("scheduleId", scheduleId);
        }
        Long userId = ticketOrderPageDTO.getUserId();
        if (userId != null ) {
            queryWrapper.eq("userId", userId);
        }
        queryWrapper.eq("cinema_id", ticketOrderPageDTO.getCinemaId());
        // 可以添加更多的查询条件
        queryWrapper.orderByDesc("updated_at");
    }

    @Override
    public boolean createTicketOrder(TicketOrderGenerationDTO ticketOrderGenerationDTO) {
        //管理员添加订单，这里的userId为管理员

        long userId = Long.parseLong(BaseContext.getCurrentPayload().get("userId"));
        int ticketCount = ticketOrderGenerationDTO.getSeatIds().size();
        //设置状态
        //TODO 这里可能要设置不同的状态哦
        BigDecimal price4One = scheduleService.getById(ticketOrderGenerationDTO.getScheduleId()).getTicketPrice();
        TicketOrder ticketOrder = TicketOrder.builder()
                .userId(userId)
                .orderNum(generateOrderNumber())
                .totalPrice(price4One.multiply(BigDecimal.valueOf(ticketCount)))
                .status(StatusConstant.NORMAL)
                .build();

        this.save(ticketOrder);

        Long orderId = ticketOrder.getId();
        if(orderId == null){
            throw new SystemException(MessageConstant.SYSTEM_ERROR);
        }

        long scheduleId = ticketOrderGenerationDTO.getScheduleId();
        for (Long seatId : ticketOrderGenerationDTO.getSeatIds()) {
            Ticket ticket = Ticket.builder()
                    .seatId(seatId)
                    .scheduleId(scheduleId)
                    .status(StatusConstant.NORMAL)
                    .build();

        }








        return true;
    }

}

