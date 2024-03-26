package com.Cybirgos.Cinema.room;

import com.Cybirgos.Cinema.diffusion.Diffusion;
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
    private int number;
    private int capacity;
    private int nbVipSeats;
    @OneToMany(mappedBy = "room")
    private List<Diffusion> diffusions;
}
