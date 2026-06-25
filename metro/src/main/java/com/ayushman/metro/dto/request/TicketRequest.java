package com.ayushman.metro.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Long sourceId;
    private Long destinationId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
}
