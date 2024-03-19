package com.Cybirgos.Cinema.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    @Autowired
    FilmRepo filmRepo;

    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addFilm(Film film) {
        var film1 = Film.builder()
                .name(film.getName())
                .duration(film.getDuration())
                .rate(film.getRate())
                .category(film.getCategory())
                .version(film.getVersion())
                .build();
        filmRepo.save(film1);
        return new ResponseEntity<>("Added",HttpStatus.OK);
    }

    public ResponseEntity<String> updateFilm(Integer id) {
        //TODO add update logic
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteFilm(Integer id) {
        var filmToDelete = filmRepo.findById(id).orElseThrow();
        filmRepo.delete(filmToDelete);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
