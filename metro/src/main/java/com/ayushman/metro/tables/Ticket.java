package com.ayushman.metro.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private Station source;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Station destination;
    private LocalDate date;
    private LocalTime time;
    private Integer price;

}
