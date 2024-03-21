package com.Cybirgos.Cinema.film;

import com.Cybirgos.Cinema.images.Image;
import com.Cybirgos.Cinema.images.ImageRepo;
import com.Cybirgos.Cinema.images.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FilmService {
    @Autowired
    FilmRepo filmRepo;
    @Autowired
    ImageRepo imageRepo;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> addFilm(Film film, MultipartFile file) throws IOException {
        Image savedImage = imageRepo.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        var film1 = Film.builder()
                .name(film.getName())
                .duration(film.getDuration())
                .rate(film.getRate())
                .category(film.getCategory())
                .version(film.getVersion())
                .poster(savedImage)
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
