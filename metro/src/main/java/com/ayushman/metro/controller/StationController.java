package com.ayushman.metro.controller;

import com.ayushman.metro.dto.response.StationResponse;
import com.ayushman.metro.service.StationService;
import lombok.RequiredArgsConstructor;
import com.ayushman.metro.dto.request.StationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest, @PathVariable Long userId) {
        StationResponse stationResponse = stationService.createStation(stationRequest, userId);
        return ResponseEntity.ok(stationResponse);
    }

    @GetMapping("/{stationId}/user/{userId}")
    public ResponseEntity<StationResponse> getStationDetailsById(@PathVariable Long stationId, @PathVariable Long userId) {
        StationResponse stationResponse = stationService.getStationDetailsById(stationId);
        return ResponseEntity.ok(stationResponse);
    }

    @DeleteMapping("/delete/{stationId}/user/{userId}")
    public ResponseEntity<String> deleteStationById(@PathVariable Long stationId, @PathVariable Long userId) {
        stationService.deleteStationById(stationId, userId);
        return ResponseEntity.ok("Station deleted successfully");
    }
}
