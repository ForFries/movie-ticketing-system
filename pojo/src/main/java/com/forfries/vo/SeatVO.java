package com.forfries.vo;

import com.forfries.entity.Seat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class SeatVO implements Serializable {
    private long colNum;
    private long rowNum;
    private long screeningHallId;
    private List<Seat> seats;
}
