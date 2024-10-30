package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScreeningHallPageDTO extends PageDTO implements Serializable {
    private Integer cinemaId;
    private String name;
}
