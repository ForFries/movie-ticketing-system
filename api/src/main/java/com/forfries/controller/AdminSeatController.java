package com.forfries.controller;

import com.forfries.dto.SeatDTO;
import com.forfries.result.Result;
import com.forfries.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin/seats")
public class AdminSeatController {
    @Autowired
    private SeatService seatService;

    @PostMapping
    public Result<?> addSeats(SeatDTO seatDTO) {
        seatService.addSeats(seatDTO);
        return Result.success();
    }
}
