package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName screening_hall
 */
@TableName(value ="screening_hall")
@Data
public class ScreeningHall implements Serializable {
    private Long id;

    private Long cinemaId;

    private String name;

    private Integer status;

    private Integer maxCapacity;

    private String introduction;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}