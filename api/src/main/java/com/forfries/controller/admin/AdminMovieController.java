package com.forfries.controller.admin;

import com.forfries.constant.MessageConstant;
import com.forfries.dto.MoviePageDTO;
import com.forfries.entity.Movie;
import com.forfries.exception.InconsistentIDException;
import com.forfries.result.PageResult;
import com.forfries.result.Result;
import com.forfries.service.common.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/movies")
public class AdminMovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public Result<PageResult> pageMovies(MoviePageDTO moviePageDTO) {
        PageResult pageResult = movieService.page(moviePageDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result<Movie> getMovieById(@PathVariable long id) {
        return Result.success(movieService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteMovieById(@PathVariable long id) {
        movieService.removeById(id);
        return Result.success();
    }

    @PostMapping
    public Result<?> addMovie(@RequestBody Movie movie) {
        movieService.save(movie);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> updateMovie(@PathVariable Long id,@RequestBody Movie movie) {
        if(!movie.getId().equals(id))
            throw new InconsistentIDException(MessageConstant.INCONSISTENT_ID);
        movieService.updateById(movie);
        return Result.success();
    }

}
