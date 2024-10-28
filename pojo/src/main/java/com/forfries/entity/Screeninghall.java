package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName screeninghall
 */
@TableName(value ="screeninghall")
@Data
public class Screeninghall implements Serializable {
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