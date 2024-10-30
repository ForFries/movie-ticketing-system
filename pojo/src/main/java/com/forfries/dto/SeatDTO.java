// Request.java

package com.forfries.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatDTO {
    private long screeningHallId;
    private long colNum;
    private long rowNum;
    private List<SeatCoordinate> seatCoordinates;

}

