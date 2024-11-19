package com.forfries.controller.admin;


import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.dto.SeatDTO;
import com.forfries.exception.RepeatCreationException;
import com.forfries.result.Result;
import com.forfries.service.ScreeningHallService;
import com.forfries.service.SeatService;
import com.forfries.vo.SeatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/admin/seats")
public class AdminSeatController {
    @Autowired
    private SeatService seatService;

    @Autowired
    private ScreeningHallService screeningHallService;

    @GetMapping
    public Result<SeatVO> getSeats(@RequestParam Long screeningHallId) {
        screeningHallService.check(screeningHallId);

        return Result.success(seatService.getSeats(screeningHallId));
    }

    @PostMapping
    public Result<?> createSeats(@RequestBody SeatDTO seatDTO) {
        screeningHallService.check(seatDTO.getScreeningHallId());

        if(!screeningHallService.getById(seatDTO.getScreeningHallId()).getStatus()
                .equals(StatusConstant.SEAT_NOT_CREATED))
            throw new RepeatCreationException(MessageConstant.REPEAT_CREATION);

        seatService.addSeats(seatDTO);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateSeats(@RequestBody SeatDTO seatDTO) {
        screeningHallService.check(seatDTO.getScreeningHallId());

        if(!screeningHallService.getById(seatDTO.getScreeningHallId()).getStatus()
                .equals(StatusConstant.SEAT_NOT_CREATED))
            seatService.deleteSeats(seatDTO.getScreeningHallId());
        seatService.addSeats(seatDTO);
        return Result.success();
    }

    @PostMapping("/{id}")
    public Result<?> updateSeatStatus(@PathVariable Long id, @RequestParam String cinemaId,@RequestBody HashMap<String,String> status) {
        //检查seatId对应的screeningHall是否属于本管理员
        screeningHallService.check(seatService.getById(id).getScreeningHallId());
        seatService.update(id,status.get("status"));
        return Result.success();
    }
}
