package com.ayushman.metro.dto.response;

import lombok.*;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationResponse {
    private Long id;
    private String name;
    private Map<Long, Double> adj;
}
