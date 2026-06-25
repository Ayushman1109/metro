package com.ayushman.metro.dto.response;

import com.ayushman.metro.entity.Ticket;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private Boolean isAdmin;
    private String name;
    private String email;
    private List<Ticket> tickets;
}
