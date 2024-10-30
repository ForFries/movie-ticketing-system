package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaPageDTO extends PageDTO implements Serializable {
    private String name;
}