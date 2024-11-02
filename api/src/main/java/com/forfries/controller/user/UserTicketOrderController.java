package com.forfries.controller.user;

import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.TicketOrder;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.ScheduleService;
import com.forfries.service.TicketOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/orders")
@Slf4j
public class UserTicketOrderController {

    @Autowired
    private TicketOrderService ticketOrderService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Result<PageResult> pageTicketOrders(TicketOrderPageDTO ticketOrderPageDTO) {

        PageResult pageResult = ticketOrderService.page(ticketOrderPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<TicketOrder> getTicketOrderById(@PathVariable long id) {
        return Result.success(ticketOrderService.getByIdWithUserCheck(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> cancelTicketOrderById(@PathVariable long id) {
        //TODO 取消订单 但是不删除订单
        ticketOrderService.cancelTicketOrderWithUserCheck(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> createTicketOrder(@RequestBody TicketOrderGenerationDTO ticketOrderGenerationDTO) {

        ticketOrderService.createTicketOrder(ticketOrderGenerationDTO);
        return Result.success();
    }


}
