package com.ayushman.metro.admin;

import com.ayushman.metro.tables.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<Station> getAllStations(){
        return adminService.getAllStations();
    }

    @PostMapping
    public Station createStation(@RequestBody Station station){
        return adminService.createStation(station);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id){
        adminService.deleteStation(id);
    }

}
