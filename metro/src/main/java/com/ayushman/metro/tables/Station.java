package com.ayushman.metro.tables;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Long,Double> adj = new HashMap<>();
}
