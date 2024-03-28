package com.Cybirgos.Cinema.diffusion;

import com.Cybirgos.Cinema.film.FilmRepo;
import com.Cybirgos.Cinema.room.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiffusionService {
    @Autowired
    DiffusionRepo diffusionRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    FilmRepo filmRepo;


    public ResponseEntity<List<Diffusion>> getDiffusionsByDateAndRoom(LocalDate date, Integer roomNumber) {
        var room = roomRepo.findByRoomNumber(roomNumber).orElseThrow();
        var diffusion = diffusionRepo.findByDateAndRoom(date,room.getId());
        return diffusion.map(diffusions -> new ResponseEntity<>(diffusions, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    public ResponseEntity<String> addDiffusion(Diffusion diffusion) {
        var film = diffusion.getFilm();
        var room = diffusion.getRoom();
        var date = diffusion.getDate();

        if(room.isOccupied()) {
            if (diffusionRepo.findByRoomId(room.getId()).isPresent()) {
                var diff = diffusionRepo.findByRoomId(room.getId()).get();
                for(Diffusion d : diff) {
                    if (d.getDate() == date) {
                        return new ResponseEntity<>("Room occupied in this day", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
            var savedDiffusion = Diffusion.builder()
                    .date(date)
                    .startTime(diffusion.getStartTime())
                    .film(film)
                    .room(room)
                    .price(diffusion.getPrice())
                    .vipPrice(diffusion.getVipPrice())
                    .nbTicketSold(0)
                    .build();
            diffusionRepo.save(savedDiffusion);
            film.setOnDiffusion(true);
            filmRepo.save(film);
            room.setOccupied(true);
            roomRepo.save(room);

        return new ResponseEntity<>("Diffusion created",HttpStatus.OK);
    }

    public ResponseEntity<String> updateDiffusion(Integer id, Diffusion diffusion) {
        var updatedDiffusion = diffusionRepo.findById(id).orElseThrow();
        var room = diffusion.getRoom();
        var date = diffusion.getDate();
        var film = diffusion.getFilm();
        if(room.isOccupied()) {
            if (diffusionRepo.findByRoomId(room.getId()).isPresent()) {
                var diff = diffusionRepo.findByRoomId(room.getId()).get();
                for(Diffusion d : diff) {
                    if (d.getDate() == date) {
                        return new ResponseEntity<>("Room occupied in this day", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
        updatedDiffusion.setDate(date);
        updatedDiffusion.setFilm(film);
        updatedDiffusion.setRoom(room);
        updatedDiffusion.setPrice(diffusion.getPrice());
        updatedDiffusion.setSchedule(diffusion.getSchedule());
        updatedDiffusion.setNbTicketSold(diffusion.getNbTicketSold());
        updatedDiffusion.setVipPrice(diffusion.getVipPrice());
        diffusionRepo.save(updatedDiffusion);
        film.setOnDiffusion(true);
        filmRepo.save(film);
        room.setOccupied(true);
        roomRepo.save(room);

        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    // Exception handling not necessary
    public ResponseEntity<String> deleteDiffusion(Integer id) {

        var diffusionToDelete = diffusionRepo.findById(id).orElseThrow();
        var room = diffusionToDelete.getRoom();
        var film = diffusionToDelete.getFilm();
        if (diffusionRepo.findByRoomId(room.getId()).isEmpty()) {
            room.setOccupied(false);
            roomRepo.save(room);
        }
        if (diffusionRepo.findByFilmId(film.getId()).isEmpty()){
            film.setOnDiffusion(false);
            filmRepo.save(film);
        }
        diffusionRepo.delete(diffusionToDelete);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    public ResponseEntity<List<Diffusion>> getAllDiffusions() {
        return new ResponseEntity<>(diffusionRepo.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<Boolean> isDiffusionFull(Integer id) {
        Diffusion diffusion;
        if(diffusionRepo.findById(id).isPresent()){
            diffusion = diffusionRepo.findById(id).get();
            if (diffusion.getNbTicketSold() == diffusion.getRoom().getCapacity()){
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
