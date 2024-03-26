package com.Cybirgos.Cinema.room;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int seatNb;
    private boolean isReserved;
    private boolean isVip;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "diffusion_id", referencedColumnName = "id")
    private Diffusion diffusion;
}
