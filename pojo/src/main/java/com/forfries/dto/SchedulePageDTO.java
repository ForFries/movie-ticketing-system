package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SchedulePageDTO extends PageDTO implements Serializable {
    private Long cinemaId;
    private Long movieId;
    private Long screeningHallId;
    private LocalDate date;
}
