package com.Cybirgos.Cinema.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/film")
@CrossOrigin
@RequiredArgsConstructor
public class FilmController {
    @Autowired
    FilmService filmService;
    @GetMapping("/getAllFilms")
    public ResponseEntity<List<Film>> getAllFilms (){
        return filmService.getAllFilms();
    }
    @PostMapping("/addFilm")
    public ResponseEntity<String> addFilm(@RequestBody Film film){
        return filmService.addFilm(film);
    }
    @PutMapping("/updateFilm/{id}")
    public ResponseEntity<String> updateFilm (@PathVariable Integer id){
        return filmService.updateFilm(id);
    }
    @DeleteMapping("/deleteFilm/{id}")
    public ResponseEntity<String > deleteFilm (@PathVariable Integer id){
        return filmService.deleteFilm(id);
    }
}
