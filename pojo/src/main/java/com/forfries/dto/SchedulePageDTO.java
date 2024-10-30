package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class SchedulePageDTO extends PageDTO implements Serializable {
    private Long cinemaId;
    private Long movieId;
    private Long screeningHallId;
    private String date;
}
