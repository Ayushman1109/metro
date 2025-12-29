package com.ayushman.metro.user;

import com.ayushman.metro.tables.Station;
import com.ayushman.metro.tables.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Data
    public class TicketRequest {
        private Long sourceId;
        private Long destinationId;
        private LocalDate date;
        private LocalTime time;
    }

    @GetMapping
    public List<Ticket> getAllTickets(){
        return userService.getAllTickets();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest){
        return userService.createTicket(
                ticketRequest.getSourceId(),
                ticketRequest.getDestinationId(),
                ticketRequest.getDate(),
                ticketRequest.getTime()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id){
        userService.deleteTicket(id);
    }
}
