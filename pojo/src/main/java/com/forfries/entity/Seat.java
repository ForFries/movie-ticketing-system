package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * @TableName seat
 */
@TableName(value ="seat")
@Data
@Builder
public class Seat implements Serializable {
    private Long id;

    private Long screeningHallId;

    private String status;

    private Integer row;

    private Integer col;

    private String rowId;

    private String colId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}