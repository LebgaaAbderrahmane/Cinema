package com.Cybirgos.Cinema.room;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int roomNumber;
    private int capacity;
    private boolean isOccupied;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Diffusion> diffusions;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Seat> seats;
}
