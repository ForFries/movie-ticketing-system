package com.forfries.controller.common;

import com.forfries.constant.NormalStatusConstant;
import com.forfries.dto.MoviePageDTO;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.CinemaService;
import com.forfries.service.MovieService;
import com.forfries.service.ScheduleService;
import com.forfries.service.ScreeningHallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ScreeningHallService screeningHallService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/movies")
    Result<PageResult> pageMovies(MoviePageDTO moviePageDTO) {
        moviePageDTO.setStatus(NormalStatusConstant.NORMAL_STATUS);
        return Result.success(movieService.page(moviePageDTO));
    }

}
