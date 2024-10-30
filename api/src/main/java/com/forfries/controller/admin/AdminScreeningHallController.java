package com.forfries.controller.admin;



import com.forfries.constant.MessageConstant;
import com.forfries.constant.StatusConstant;
import com.forfries.dto.ScreeningHallPageDTO;
import com.forfries.entity.ScreeningHall;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.ScreeningHallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/screeninghalls")
@Slf4j
public class AdminScreeningHallController {

    @Autowired
    private ScreeningHallService screeningHallService;

    @GetMapping
    public Result<PageResult> pageScreeningHalls(ScreeningHallPageDTO screeningHallPageDTO) {

        PageResult pageResult = screeningHallService.page(screeningHallPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<ScreeningHall> getScreeningHallById(@PathVariable long id) {
        return Result.success(screeningHallService.getByIdWithCheck(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteScreeningHallById(@PathVariable long id) {
        screeningHallService.deleteByIdWithCheck(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> addScreeningHall(@RequestParam Long cinemaId,
                                      @RequestBody ScreeningHall screeningHall) {
        //TODO 这里存在一些问题，刚创建好的ScreeningHall的Status应该为无座位状态，后续再进行更改
        if(!screeningHall.getCinemaId().equals(cinemaId))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_CINEMA_ID);

        screeningHall.setStatus(StatusConstant.SEAT_NOT_CREATED);
        screeningHallService.save(screeningHall);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> updateScreeningHall(@PathVariable Long id,
                                         @RequestParam Long cinemaId,
                                         @RequestBody ScreeningHall screeningHall) {
        if(!screeningHall.getId().equals(id))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_ID);

        if(!screeningHall.getCinemaId().equals(cinemaId))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_CINEMA_ID);

        screeningHallService.updateByIdWithCheck(id,screeningHall);
        return Result.success();
    }

}
