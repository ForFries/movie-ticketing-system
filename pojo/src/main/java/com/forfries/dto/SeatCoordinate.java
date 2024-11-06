package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeatCoordinate implements Serializable {
    private int row;
    private int col;
}
