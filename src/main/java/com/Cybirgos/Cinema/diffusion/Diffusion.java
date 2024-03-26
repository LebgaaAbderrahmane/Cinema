package com.Cybirgos.Cinema.diffusion;

import com.Cybirgos.Cinema.film.Film;
import com.Cybirgos.Cinema.room.Room;
import com.Cybirgos.Cinema.schedule.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Diffusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private int nbTicket;
    private double price;
    private double vipPrice;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
