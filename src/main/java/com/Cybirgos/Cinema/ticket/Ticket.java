package com.Cybirgos.Cinema.ticket;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.Cybirgos.Cinema.room.Seat;
import com.Cybirgos.Cinema.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int number;
    private double price;
    private LocalDateTime createdOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "diffusion_id", referencedColumnName = "id")
    private Diffusion diffusion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
