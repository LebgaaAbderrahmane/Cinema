package com.Cybirgos.Cinema.film;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Time duration;
    @Enumerated(EnumType.STRING)
    private Version version;
    private int rate;
    private List<String> category;
    @OneToMany(mappedBy = "film")
    private List<Diffusion> diffusions;

}
