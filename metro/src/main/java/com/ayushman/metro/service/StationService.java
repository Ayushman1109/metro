package com.ayushman.metro.service;

import com.ayushman.metro.dto.request.StationRequest;
import com.ayushman.metro.dto.response.StationResponse;
import com.ayushman.metro.entity.Station;
import com.ayushman.metro.entity.User;
import com.ayushman.metro.repository.StationRepository;
import com.ayushman.metro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final UserRepository userRepository;

    public StationResponse createStation(StationRequest stationRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            throw new RuntimeException("User is not authorized to create a station");
        }
        Station station = new Station();
        station.setName(stationRequest.getName());
        if (stationRequest.getAdj() != null) {
            station.setAdj(stationRequest.getAdj());
        }
        station = stationRepository.save(station);
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .adj(station.getAdj())
                .build();
    }

    public StationResponse getStationDetailsById(Long stationId){
        return stationRepository.findById(stationId)
                .map(station -> StationResponse.builder()
                        .id(station.getId())
                        .name(station.getName())
                        .adj(station.getAdj())
                        .build())
                .orElseThrow(() -> new RuntimeException("Station not found with id: " + stationId));
    }

    public void deleteStationById(Long stationId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            throw new RuntimeException("User is not authorized to delete a station");
        }
        
        if (!stationRepository.existsById(stationId)) {
            throw new RuntimeException("Station not found with id: " + stationId);
        }
        stationRepository.deleteById(stationId);
    }
}
