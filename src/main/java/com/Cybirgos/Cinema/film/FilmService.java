package com.Cybirgos.Cinema.film;

import com.Cybirgos.Cinema.images.Image;
import com.Cybirgos.Cinema.images.ImageRepo;
import com.Cybirgos.Cinema.images.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    FilmRepo filmRepo;
    @Autowired
    ImageRepo imageRepo;

    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmRepo.findAll(), HttpStatus.OK);
    }
    public byte[] getImage(Long id){
        Optional<Image> dbImageData = imageRepo.findById(id);
        return ImageUtils.decompressImage(dbImageData.get().getImageData());
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
                .description(film.getDescription())
                .poster(savedImage)
                .build();
        filmRepo.save(film1);

        return new ResponseEntity<>("Added",HttpStatus.OK);
    }

    public ResponseEntity<String> updateFilm(Integer id, Film film) {
        Film updatedFilm = filmRepo.findById(id).get();
        updatedFilm.setName(film.getName());
        updatedFilm.setCategory(film.getCategory());
        updatedFilm.setDescription(film.getDescription());
        updatedFilm.setRate(film.getRate());
        updatedFilm.setVersion(film.getVersion());
        //filmRepo.save(updatedFilm);
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }


    public ResponseEntity<String> deleteFilm(Integer id) {
        var filmToDelete = filmRepo.findById(id).orElseThrow();
        filmRepo.delete(filmToDelete);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    public List<byte[]> getAllImages() {
        List<Image> dbImageData = imageRepo.findAll();
        List<byte[]> posters = null;
        for(Image i : dbImageData){
            posters.add(ImageUtils.decompressImage(i.getImageData()));
        }
        return posters;
    }

    public ResponseEntity<?> getFilmById(Integer id) {
        try {
            return new ResponseEntity<>(filmRepo.findById(id).orElseThrow(), HttpStatus.OK);
        }catch (Exception ignored){}
        return new ResponseEntity<>("No value present",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Film>> getFilmByCategory(String category) {
        try {
            return new ResponseEntity<>(filmRepo.findByCategory(category),HttpStatus.OK);
        }catch (Exception ignored){}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    public ResponseEntity<List<Film>> getFilmByVersion(String version) {
        return new ResponseEntity<>(filmRepo.findByVersion(version),HttpStatus.OK);
    }

    public ResponseEntity<List<Film>> getFilmByName(String name) {
        try {
            var films = filmRepo.findByName(name);
            return new ResponseEntity<>(films, HttpStatus.OK);
        }catch (Exception ignored){}
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateFilmPoster(Integer id, MultipartFile file) throws IOException {
        Image savedImage = imageRepo.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        var film = filmRepo.findById(id).get();
        film.setPoster(savedImage);
        return new ResponseEntity<>("updated",HttpStatus.OK);
    }
}
