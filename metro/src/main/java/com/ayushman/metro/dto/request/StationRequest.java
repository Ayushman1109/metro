package com.ayushman.metro.dto.request;

import lombok.*;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationRequest {
    private Long id;
    private String name;
    private Map<Long, Double> adj;
}
