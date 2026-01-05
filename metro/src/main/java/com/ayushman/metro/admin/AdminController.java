package com.ayushman.metro.admin;

import com.ayushman.metro.tables.Station;
import com.ayushman.metro.tables.Ticket;
import com.ayushman.metro.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ayushman.metro.common.TicketRequest;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private UserService userService;

    @GetMapping("/station")
    public List<Station> getAllStations(){
        return adminService.getAllStations();
    }

    @PostMapping("/station")
    public Station createStation(@RequestBody Station station){
        return adminService.createStation(station);
    }

    @DeleteMapping("/station/{id}")
    public void deleteStation(@PathVariable Long id){
        adminService.deleteStation(id);
    }

    @DeleteMapping("/ticket/{id}")
    public void deleteTicket(@PathVariable Long id){
        userService.deleteTicket(id);
    }

    @GetMapping("/ticket")
    public List<Ticket> getAllTickets(){
        return userService.getAllTickets();
    }

    @PostMapping("/ticket")
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest){
        return userService.createTicket(
                ticketRequest.getSourceId(),
                ticketRequest.getDestinationId(),
                ticketRequest.getDate(),
                ticketRequest.getTime()
        );
    }

}
