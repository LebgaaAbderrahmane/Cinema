package com.Cybirgos.Cinema.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @GetMapping("getFilmByCategory")
    public ResponseEntity<List<Film>> getFilmByCategory (@RequestParam String category){
        return filmService.getFilmByCategory(category);
    }
    @GetMapping("getFilmByVersion")
    public ResponseEntity<List<Film>> getFilmByVersion (@RequestParam String version){
        return filmService.getFilmByVersion(version);
    }
    @GetMapping("getFilmByName/{name}")
    public ResponseEntity<List<Film>> getFilmByName (@PathVariable String name){
        return filmService.getFilmByName(name);
    }

    @GetMapping("getFilmById/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Integer id){
        return filmService.getFilmById(id);
    }
    @GetMapping("/getAllPosters") // Not working
    public ResponseEntity<?> getAllPosters (){
        List<byte[]> imageData=filmService.getAllImages();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
    @GetMapping("/getFilmPoster/{id}")
    public ResponseEntity<?> getFilmPoster (@PathVariable Long id){
        byte[] imageData=filmService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
    @PutMapping("/updateFilmPoster/{id}")
    public ResponseEntity<String> updateFilmPoster(@PathVariable Integer id,@RequestPart MultipartFile file) throws IOException {
        return filmService.updateFilmPoster(id,file);
    }
    @RequestMapping(path = "/addFilm", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addFilm(@RequestPart("filmData") Film film , @RequestPart("image") MultipartFile file) throws IOException {
        return filmService.addFilm(film,file);
    }
    @PutMapping("/updateFilm/{id}")
    public ResponseEntity<String> updateFilm (@PathVariable Integer id,@RequestBody Film film){
        return filmService.updateFilm(id,film);
    }
    @DeleteMapping("/deleteFilm/{id}")
    public ResponseEntity<String > deleteFilm (@PathVariable Integer id){
        return filmService.deleteFilm(id);
    }
}
