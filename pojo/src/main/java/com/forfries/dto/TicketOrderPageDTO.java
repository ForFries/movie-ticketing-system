package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketOrderPageDTO extends PageDTO implements Serializable {

    private Long cinemaId;
    private Long scheduleId;
    private Long userId;
    private String orderNum;
}
