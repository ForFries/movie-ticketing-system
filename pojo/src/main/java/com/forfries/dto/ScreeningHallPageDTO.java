package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScreeningHallPageDTO extends PageDTO implements Serializable {
    private Long cinemaId;
    private String name;
}
