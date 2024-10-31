package com.forfries.controller.admin;



import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.dto.TicketOrderGenerationDTO;
import com.forfries.dto.TicketOrderPageDTO;
import com.forfries.entity.TicketOrder;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.TicketOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/orders")
@Slf4j
public class AdminTicketOrderController {

    @Autowired
    private TicketOrderService ticketOrderService;

    @GetMapping
    public Result<PageResult> pageTicketOrders(TicketOrderPageDTO ticketOrderPageDTO) {

        PageResult pageResult = ticketOrderService.page(ticketOrderPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<TicketOrder> getTicketOrderById(@PathVariable long id) {
        return Result.success(ticketOrderService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteTicketOrderById(@PathVariable long id) {

        return Result.success();
    }

    @PostMapping
    public Result<?> createTicketOrder(@RequestParam Long cinemaId,
                                      @RequestBody TicketOrderGenerationDTO ticketOrderGenerationDTO) {

        ticketOrderService.createTicketOrder(ticketOrderGenerationDTO);
        return Result.success();
    }


}
