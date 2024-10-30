package com.forfries.controller.admin;



import com.forfries.constant.MessageConstant;
import com.forfries.dto.CinemaPageDTO;
import com.forfries.entity.Cinema;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
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

    @GetMapping
    public Result<PageResult> pageCinemas(CinemaPageDTO cinemaPageDTO) {
        PageResult pageResult = cinemaService.page(cinemaPageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Cinema> getCinemaById(@PathVariable long id) {
        return Result.success(cinemaService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCinemaById(@PathVariable long id) {
        cinemaService.removeById(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> addCinema(@RequestBody Cinema cinema) {
        cinemaService.save(cinema);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> updateCinema(@PathVariable Long id,@RequestBody Cinema cinema) {
        if(!cinema.getId().equals(id))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_ID);
        cinemaService.updateById(cinema);
        return Result.success();
    }

}
