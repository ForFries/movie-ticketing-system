package com.forfries.controller.admin;



import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.TicketOrder;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.common.ScheduleService;
import com.forfries.service.common.TicketOrderService;
import com.forfries.service.webscket.AdminWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/orders")
@Slf4j
public class AdminTicketOrderController {

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AdminWebSocketService adminWebSocketService;

    @GetMapping
    public Result<PageResult> pageTicketOrders(TicketOrderPageDTO ticketOrderPageDTO) {

        PageResult pageResult = ticketOrderService.page(ticketOrderPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<TicketOrder> getTicketOrderById(@PathVariable long id) {
        return Result.success(ticketOrderService.getByIdWithCheck(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> cancelTicketOrderById(@PathVariable long id) {
        //TODO 取消订单 但是不删除订单
        ticketOrderService.cancelTicketOrderWithCheck(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> createTicketOrder(@RequestBody TicketOrderGenerationDTO ticketOrderGenerationDTO) {
        //影院管理员权限认证
        scheduleService.check(ticketOrderGenerationDTO.getScheduleId());

        return Result.success(ticketOrderService.createTicketOrder(ticketOrderGenerationDTO));
    }


}
