package com.forfries.controller.admin;



import com.forfries.constant.MessageConstant;
import com.forfries.entity.Cinema;
import com.forfries.result.Result;
import com.forfries.service.CinemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/cinemas")
@Slf4j
public class AdminCinemaController {

    @Autowired
    private CinemaService cinemaService;


    @GetMapping("/{id}")
    public Result<Cinema> getCinemaById(@PathVariable int id) {
        return Result.success(cinemaService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result deleteCinemaById(@PathVariable int id) {
        cinemaService.removeById(id);
        return Result.success();
    }
}
