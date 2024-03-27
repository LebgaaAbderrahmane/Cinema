package com.Cybirgos.Cinema.diffusion;

import com.Cybirgos.Cinema.film.FilmRepo;
import com.Cybirgos.Cinema.room.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiffusionService {
    @Autowired
    DiffusionRepo diffusionRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    FilmRepo filmRepo;


    public ResponseEntity<List<Diffusion>> getAllDiffusions(Date date, Integer roomId) {
        return new ResponseEntity<>(diffusionRepo.findByDateAndRoom(date,roomId), HttpStatus.OK);
    }

    public ResponseEntity<String> addDiffusion(Diffusion diffusion) {
        var film = diffusion.getFilm();
        var room = diffusion.getRoom();
        var date = diffusion.getDate();
        if(!room.isOccupied()) {
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

            return new ResponseEntity<>("Added", HttpStatus.OK);
        }
        return new ResponseEntity<>("Room occupied",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateDiffusion(Integer id, Diffusion diffusion) {
        var updatedDiffusion = diffusionRepo.findById(id).orElseThrow();
        updatedDiffusion.setDate(diffusion.getDate());
        updatedDiffusion.setFilm(diffusion.getFilm());
        updatedDiffusion.setRoom(diffusion.getRoom());
        updatedDiffusion.setPrice(diffusion.getPrice());
        updatedDiffusion.setSchedule(diffusion.getSchedule());
        updatedDiffusion.setNbTicketSold(diffusion.getNbTicketSold());
        updatedDiffusion.setVipPrice(diffusion.getVipPrice());
        //diffusionRepo.save(updatedDiffusion);
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDiffusion(Integer id) {
        //TODO Reset room
        var diffusionToDelete = diffusionRepo.findById(id).orElseThrow();
        diffusionRepo.delete(diffusionToDelete);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
