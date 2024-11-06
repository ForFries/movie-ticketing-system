package com.forfries.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TicketOrderGenerationDTO implements Serializable {
    private long scheduleId;
    private List<Long> seatIds;
}
