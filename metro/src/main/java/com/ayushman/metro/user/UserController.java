package com.ayushman.metro.user;

import com.ayushman.metro.common.TicketRequest;
import com.ayushman.metro.tables.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest){
        return userService.bookTicket(
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
