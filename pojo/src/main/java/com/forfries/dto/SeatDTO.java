// Request.java

package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SeatDTO implements Serializable {
    private Long screeningHallId;
    private long colCount;
    private long rowCount;
    private List<SeatCoordinate> seatCoordinates;

}

