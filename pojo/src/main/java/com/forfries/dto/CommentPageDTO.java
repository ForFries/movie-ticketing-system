package com.forfries.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CommentPageDTO extends PageDTO implements Serializable {
    private Long movieId;
    private Long userId;
}