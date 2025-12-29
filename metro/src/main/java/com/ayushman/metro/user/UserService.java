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

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Long sourceId, Long destinationId, LocalDate date, LocalTime time){
        Ticket ticket = Ticket.builder()
                .source(stationRepository.findById(sourceId).orElseThrow())
                .destination(stationRepository.findById(destinationId).orElseThrow())
                .price(getDist(sourceId, destinationId))
                .date(date)
                .time(time)
                .build();
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        ticketRepository.delete(ticket);
    }

    public Integer getDist(Long station1Id, Long station2Id){
        Integer dist = 0;
        Queue<Long> q = new ArrayDeque<>();
        q.offer(station1Id);
        Map<Long, Long> m = new HashMap<>();
        m.put(station1Id, station1Id);
        while(!q.isEmpty()){
            Long id = q.poll();
            Station station = stationRepository.findById(id).orElseThrow();
            if(station.getId().equals(station2Id)) break;
            List<Long> adj = station.getAdj();
            for(Long i : adj){
                if(!m.containsKey(i)) {
                    q.offer(i);
                    m.put(i, station.getId());
                }
            }
        }
        if (!m.containsKey(station2Id)) {
            return -1;
        }
        Long stationId = station2Id;
        while(!(stationId.equals(station1Id))){
            dist++;
            stationId = m.get(stationId);
        }
        return dist;
    }

}
