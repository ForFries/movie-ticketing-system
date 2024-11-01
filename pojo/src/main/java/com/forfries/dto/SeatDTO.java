// Request.java

package com.forfries.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeatDTO {
    private long screeningHallId;
    private long colCount;
    private long rowCount;
    private List<SeatCoordinate> seatCoordinates;

}

