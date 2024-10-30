package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName schedule
 */
@TableName(value ="schedule")
@Data
public class Schedule implements Serializable {
    private Long id;

    private Long movieId;

    private Long cinemaId;

    private Long screeningHallId;

    private String status;

    private Integer currentCapacity;

    private Integer availableCapacity;

    private String startTime;

    private String endTime;

    private String ticketPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}