package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName movie
 */
@TableName(value ="movie")
@Data
public class Movie implements Serializable {
    private Long id;

    private String title;

    private String status;

    private String category;

    private String director;

    private String releaseTime;

    private String duration;

    private String imageUrl;

    private String ratings;

    private String followers;

    private String introduction;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}