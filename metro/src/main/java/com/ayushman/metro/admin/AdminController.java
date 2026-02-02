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

    @GetMapping("/station")
    public List<Station> getAllStations(){
        return adminService.getAllStations();
    }

    @PostMapping("/create/station")
    public Station createStation(@RequestBody Station station){
        return adminService.createStation(station);
    }

    @DeleteMapping("/delete/station/{id}")
    public void deleteStation(@PathVariable Long id){
        adminService.deleteStation(id);
    }

    @DeleteMapping("/delete/ticket/{id}")
    public void deleteTicket(@PathVariable Long id){
        adminService.deleteTicket(id);
    }

    @GetMapping("/ticket")
    public List<Ticket> getAllTickets(){
        return adminService.getAllTickets();
    }

    @PostMapping("/create/ticket")
    public Ticket createTicket(@RequestBody TicketRequest ticketRequest){
        return adminService.createTicket(ticketRequest);
    }

}
