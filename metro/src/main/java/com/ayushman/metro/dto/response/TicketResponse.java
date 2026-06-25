package com.ayushman.metro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private Long userId;
    private Long sourceId;
    private Long destinationId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Integer price;
}
