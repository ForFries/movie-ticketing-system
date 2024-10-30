package com.forfries.controller.admin;



import com.forfries.constant.MessageConstant;
import com.forfries.dto.SchedulePageDTO;
import com.forfries.entity.Schedule;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/schedules")
@Slf4j
public class AdminScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public Result<PageResult> pageSchedules(SchedulePageDTO schedulePageDTO) {

        PageResult pageResult = scheduleService.page(schedulePageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Schedule> getScheduleById(@PathVariable long id) {
        return Result.success(scheduleService.getByIdWithCheck(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteScheduleById(@PathVariable long id) {
        scheduleService.deleteByIdWithCheck(id);
        return Result.success();
    }
    //TODO 时间不应该冲突
    @PostMapping
    public Result<?> addSchedule(@RequestParam Long cinemaId,
                                      @RequestBody Schedule schedule) {

        if(!schedule.getCinemaId().equals(cinemaId))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_CINEMA_ID);

        scheduleService.addWithoutConflict(schedule);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> updateSchedule(@PathVariable Long id,
                                         @RequestParam Long cinemaId,
                                         @RequestBody Schedule schedule) {
        if(!schedule.getId().equals(id))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_ID);

        if(!schedule.getCinemaId().equals(cinemaId))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_CINEMA_ID);

        scheduleService.updateByIdWithCheckWithoutConflict(id,schedule);
        return Result.success();
    }

}
