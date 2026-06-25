package com.ayushman.metro.service;

import com.ayushman.metro.algorithms.MetroPriceHandler;
import com.ayushman.metro.dto.request.TicketRequest;
import com.ayushman.metro.dto.response.TicketResponse;
import com.ayushman.metro.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final TicketRepository ticketRepository;
    private final MetroPriceHandler metroPriceHandler;

    public TicketResponse bookTicket(TicketRequest ticketRequest, Long userId) {
        Integer price = metroPriceHandler.getPrice(ticketRequest.getSourceId(),
                ticketRequest.getDestinationId());
        
        com.ayushman.metro.entity.Ticket ticket = com.ayushman.metro.entity.Ticket.builder()
                .userId(userId)
                .sourceId(ticketRequest.getSourceId())
                .destinationId(ticketRequest.getDestinationId())
                .bookingDate(ticketRequest.getBookingDate())
                .bookingTime(ticketRequest.getBookingTime())
                .price(price)
                .build();
                
        ticket = ticketRepository.save(ticket);

        return TicketResponse.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .sourceId(ticket.getSourceId())
                .destinationId(ticket.getDestinationId())
                .bookingDate(ticket.getBookingDate())
                .bookingTime(ticket.getBookingTime())
                .price(ticket.getPrice())
                .build();
    }

    public TicketResponse getTicketDetailsById(Long ticketId, Long userId) {
        return ticketRepository.findById(ticketId)
                .map(ticket -> {
                    if (!ticket.getUserId().equals(userId)) {
                        throw new RuntimeException("User is not authorized to access this ticket");
                    }
                    return TicketResponse.builder()
                        .id(ticket.getId())
                        .userId(ticket.getUserId())
                        .sourceId(ticket.getSourceId())
                        .destinationId(ticket.getDestinationId())
                        .bookingDate(ticket.getBookingDate())
                        .bookingTime(ticket.getBookingTime())
                        .price(ticket.getPrice())
                        .build();
                })
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
    }

    public void cancelTicket(Long ticketId, Long userId) {
        com.ayushman.metro.entity.Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        if (!ticket.getUserId().equals(userId)) {
            throw new RuntimeException("User is not authorized to cancel this ticket");
        }
        ticketRepository.deleteById(ticketId);
    }
}
