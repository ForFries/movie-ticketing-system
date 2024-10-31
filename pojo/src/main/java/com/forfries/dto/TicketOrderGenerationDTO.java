package com.forfries.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketOrderGenerationDTO {
    private long scheduleId;
    private List<Long> seatIds;
}
