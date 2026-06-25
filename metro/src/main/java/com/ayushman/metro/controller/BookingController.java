package com.ayushman.metro.controller;

import com.ayushman.metro.dto.request.TicketRequest;
import com.ayushman.metro.dto.response.TicketResponse;
import com.ayushman.metro.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/book/{userId}")
    public ResponseEntity<TicketResponse> bookTicket(@RequestBody TicketRequest ticketRequest, @PathVariable Long userId) {
        TicketResponse ticketResponse = bookingService.bookTicket(ticketRequest, userId);
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("/{ticketId}/user/{userId}")
    public ResponseEntity<TicketResponse> getTicketDetailsById(@PathVariable Long ticketId, @PathVariable Long userId) {
        TicketResponse ticketResponse = bookingService.getTicketDetailsById(ticketId, userId);
        return ResponseEntity.ok(ticketResponse);
    }

    @DeleteMapping("/delete/{ticketId}/user/{userId}")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId, @PathVariable Long userId) {
        bookingService.cancelTicket(ticketId, userId);
        return ResponseEntity.ok("Ticket cancelled successfully");
    }
}
