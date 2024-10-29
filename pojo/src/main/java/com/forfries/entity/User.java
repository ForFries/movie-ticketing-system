package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Integer status;

    private String role;

    private Long cinemaId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}