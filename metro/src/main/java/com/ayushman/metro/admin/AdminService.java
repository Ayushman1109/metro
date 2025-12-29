package com.ayushman.metro.admin;

import com.ayushman.metro.tables.Station;
import com.ayushman.metro.repository.StationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AdminService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public Station createStation(Station station){
        Station newStation = stationRepository.save(station);
        if(newStation.getAdj() != null) {
            for (Long i : newStation.getAdj()) {
                Station neigh = stationRepository.findById(i).orElseThrow();
                List<Long> adj = neigh.getAdj();
                if (!adj.contains(newStation.getId())) {
                    adj.add(newStation.getId());
                    neigh.setAdj(adj);
                    stationRepository.save(neigh);
                }
            }
        }
        return newStation;
    }

    public void deleteStation(Long id){
        Station station = stationRepository.findById(id).orElseThrow();
        for(Long i : station.getAdj()){
            Station neigh = stationRepository.findById(i).orElseThrow();
            List<Long> adj = neigh.getAdj();
            adj.remove(station.getId());
            neigh.setAdj(adj);
            stationRepository.save(neigh);
        }
        stationRepository.delete(station);
    }
}
