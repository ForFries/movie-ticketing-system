package com.forfries.controller.common;

import com.forfries.constant.NormalStatusConstant;
import com.forfries.dto.CinemaPageDTO;
import com.forfries.dto.CommentPageDTO;
import com.forfries.dto.MoviePageDTO;
import com.forfries.dto.SchedulePageDTO;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.common.*;
import com.forfries.vo.ScheduleSeatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private TicketOrderService ticketOrderService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/movies")
    Result<PageResult> pageMovies(MoviePageDTO moviePageDTO) {
        moviePageDTO.setStatus(NormalStatusConstant.NORMAL_STATUS);
        return Result.success(movieService.page(moviePageDTO));
    }

    @GetMapping("/cinemas")
    Result<PageResult> pageCinemas(CinemaPageDTO cinemaPageDTO) {
        cinemaPageDTO.setStatus(NormalStatusConstant.NORMAL_STATUS);
        return Result.success(cinemaService.page(cinemaPageDTO));
    }

    @GetMapping("/schedules")
    Result<PageResult> pageSchedules(SchedulePageDTO schedulePageDTO) {
        schedulePageDTO.setStatus(NormalStatusConstant.NORMAL_STATUS);
        return Result.success(scheduleService.page(schedulePageDTO));
    }

   @GetMapping("/schedules/{scheduleId}/seats")
    Result<ScheduleSeatVO> getScheduleSeats(@PathVariable Long scheduleId) {

       return Result.success(ticketOrderService.getScheduleSeats(scheduleId));
   }

    @GetMapping("/movies/{id}/comments")
    Result<PageResult> pageComments(@PathVariable Long id,
            CommentPageDTO commentPageDTO){
        commentPageDTO.setMovieId(id);
        commentPageDTO.setStatus(NormalStatusConstant.NORMAL_STATUS);
        return Result.success(commentService.page(commentPageDTO));
    }

    @GetMapping("/movies/{id}")
    Result<?> getMovieById(@PathVariable Long id) {
        return Result.success(movieService.getById(id));
    }
    @GetMapping("/cinemas/{id}")
    Result<?> getCinemaById(@PathVariable Long id) {
        return Result.success(cinemaService.getById(id));
    }
    @GetMapping("/schedules/{id}")
    Result<?> getScheduleById(@PathVariable Long id) {
        return Result.success(scheduleService.getById(id));
    }
}
