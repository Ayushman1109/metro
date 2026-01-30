package com.ayushman.metro.admin;

import com.ayushman.metro.common.TicketRequest;
import com.ayushman.metro.repository.TicketRepository;
import com.ayushman.metro.tables.Station;
import com.ayushman.metro.repository.StationRepository;
import com.ayushman.metro.tables.Ticket;
import com.ayushman.metro.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
@Transactional
public class AdminService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Station createStation(Station station){
        Station newStation = stationRepository.save(station);
        if(newStation.getAdj() != null) {
            Map<Long,Double> newStationAdj = newStation.getAdj();
            for (Long i : newStationAdj.keySet()) {
                Station neigh = stationRepository.findById(i).orElseThrow();
                if (!neigh.getAdj().containsKey(newStation.getId())) {
                    Map<Long,Double> adj = neigh.getAdj();
                    adj.put(newStation.getId(), newStationAdj.get(i));
                    neigh.setAdj(adj);
                    stationRepository.save(neigh);
                }
            }
        }
        else {
            newStation.setAdj(new HashMap<>());
            stationRepository.save(newStation);
        }
        return newStation;
    }

    public void deleteStation(Long id){
        Station station = stationRepository.findById(id).orElseThrow();
        Map<Long,Double> stationAdj = station.getAdj();
        for(Long i : stationAdj.keySet()){
            Station neigh = stationRepository.findById(i).orElseThrow();
            Map<Long, Double> adj = neigh.getAdj();
            adj.remove(station.getId());
            neigh.setAdj(adj);
            stationRepository.save(neigh);
        }
        stationRepository.delete(station);
    }

    public Ticket createTicket(TicketRequest ticketRequest){
        return userService.bookTicket(
                ticketRequest.getSourceId(),
                ticketRequest.getDestinationId(),
                ticketRequest.getDate(),
                ticketRequest.getTime()
        );
    }

    public void deleteTicket(@PathVariable Long id){
        userService.deleteTicket(id);
    }

}
