package com.Cybirgos.Cinema.film;

import com.Cybirgos.Cinema.diffusion.Diffusion;
import com.Cybirgos.Cinema.images.Image;
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
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double duration;
    private String version;
    private double rate;
    private String category;
    private boolean isOnDiffusion;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "film")
    private List<Diffusion> diffusions;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image poster;

}
