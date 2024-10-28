package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName cinema
 */
@TableName(value ="cinema")
@Data
public class Cinema implements Serializable {
    private Long id;

    private String name;

    private String status;

    private String address;

    private String phoneNumber;

    private String introduction;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}