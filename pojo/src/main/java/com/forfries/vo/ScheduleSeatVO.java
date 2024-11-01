package com.forfries.vo;

import com.forfries.entity.Seat;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ScheduleSeatVO {

    private long movieId;
    private String movieTitle;

    private long cinemaId;
    private String cinemaName;

    private long screeningHallId;
    private String screeningHallName;

    private long scheduleId;
    private String scheduleInfo;

    private long rowCount;
    private long colCount;

    private List<Seat> seats;

}
