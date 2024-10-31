package com.forfries.controller.admin;


import com.forfries.dto.SeatDTO;
import com.forfries.result.Result;
import com.forfries.service.ScreeningHallService;
import com.forfries.service.SeatService;
import com.forfries.vo.SeatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/seats")
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
    public Result<?> createSeat(@RequestBody SeatDTO seatDTO) {
        screeningHallService.check(seatDTO.getScreeningHallId());

        seatService.addSeats(seatDTO);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateSeat(@RequestBody SeatDTO seatDTO) {
        screeningHallService.check(seatDTO.getScreeningHallId());

        seatService.deleteSeats(seatDTO.getScreeningHallId());
        seatService.addSeats(seatDTO);
        return Result.success();
    }
}
