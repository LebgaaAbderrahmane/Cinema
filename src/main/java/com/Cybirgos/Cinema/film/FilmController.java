package com.Cybirgos.Cinema.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/film")
@CrossOrigin
@RequiredArgsConstructor
public class FilmController {
    @Autowired
    FilmService filmService;
    @GetMapping("/getAllFilms")
    public ResponseEntity<List<Film>> getAllFilms ()  {
        return filmService.getAllFilms();
    }
    @PostMapping(path = "/addFilm", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addFilm(@RequestBody Film film, @RequestParam("image") MultipartFile file) throws IOException {
        return filmService.addFilm(film,file);
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
