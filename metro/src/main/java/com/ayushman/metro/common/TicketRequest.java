package com.ayushman.metro.common;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketRequest {
    private Long sourceId;
    private Long destinationId;
    private LocalDate date;
    private LocalTime time;
}