package com.forfries.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

/**
 * @TableName ticket_order
 */
@TableName(value ="ticket_order")
@Data
@Builder
public class TicketOrder implements Serializable {
    private Long id;

    private Long userId;

    private String orderNum;

    private Long movieId;
    private String movieTitle;
    private String movieImageUrl;
    private Long cinemaId;
    private String cinemaName;
    private Long screeningHallId;
    private String screeningHallName;
    private Long scheduleId;
    private String scheduleInfo;

    private String seatInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private static final long serialVersionUID = 1L;
}