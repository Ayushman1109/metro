package com.ayushman.metro.tables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> adj = new ArrayList<>();

}
