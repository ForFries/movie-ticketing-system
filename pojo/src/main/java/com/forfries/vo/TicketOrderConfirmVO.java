package com.forfries.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forfries.entity.Seat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class TicketOrderConfirmVO implements Serializable {
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
}
