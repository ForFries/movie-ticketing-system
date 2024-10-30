package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName ticket_order
 */
@TableName(value ="ticket_order")
@Data
public class TicketOrder implements Serializable {
    private Long id;

    private Long userId;

    private String orderNum;

    private String totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}