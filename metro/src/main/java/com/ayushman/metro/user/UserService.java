package com.ayushman.metro.user;

import com.ayushman.metro.repository.StationRepository;
import com.ayushman.metro.tables.Station;
import com.ayushman.metro.tables.Ticket;
import com.ayushman.metro.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStations(){
        return stationRepository.findAll();
    }

    public Ticket bookTicket(Long sourceId, Long destinationId, LocalDate date, LocalTime time){
        Ticket ticket = Ticket.builder()
                .source(stationRepository.findById(sourceId).orElseThrow())
                .destination(stationRepository.findById(destinationId).orElseThrow())
                .price(getPrice(sourceId, destinationId))
                .date(date)
                .time(time)
                .build();
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticketRepository.delete(ticket);
    }

    public Double getDist(Long station1Id, Long station2Id){
        Map<Long, Double> shortestDistances = new HashMap<>();
        shortestDistances.put(station1Id, 0.0);

        PriorityQueue<Map.Entry<Long, Double>> q = new PriorityQueue<>(Map.Entry.comparingByValue());
        q.offer(new AbstractMap.SimpleEntry<>(station1Id, 0.0));

        while(!q.isEmpty()){
            Map.Entry<Long, Double> curr = q.poll();
            Long id = curr.getKey();
            Double distance = curr.getValue();

            if (id.equals(station2Id)) {
                return distance;
            }
            if (distance > shortestDistances.getOrDefault(id, Double.MAX_VALUE)) {
                continue;
            }

            Station station = stationRepository.findById(id).orElseThrow();
            Map<Long, Double> adj = station.getAdj();

            for (Long neighbourId: adj.keySet()) {
                if (adj.get(neighbourId) == null) continue;
                Double newDist = distance + adj.get(neighbourId);
                if (newDist < shortestDistances.getOrDefault(neighbourId, Double.MAX_VALUE)) {
                    shortestDistances.put(neighbourId, newDist);
                    q.offer(new AbstractMap.SimpleEntry<>(neighbourId, newDist));
                }
            }
        }
        throw new NoSuchElementException("No path found between the selected stations");
    }

    public Integer getPrice(Long station1Id, Long station2Id){
        Double distance = getDist(station1Id, station2Id);
        if(distance < 5.0) return 20;
        else if(distance < 12.0) return 30;
        else return 40;
    }

}
