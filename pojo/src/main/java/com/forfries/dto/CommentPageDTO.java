package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentPageDTO extends PageDTO implements Serializable {
    private Long movieId;
}