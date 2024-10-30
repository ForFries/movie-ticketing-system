package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoviePageDTO extends PageDTO implements Serializable {
    private String title;
}
