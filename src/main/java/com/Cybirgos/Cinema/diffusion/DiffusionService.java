package com.Cybirgos.Cinema.diffusion;

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

    public ResponseEntity<List<Diffusion>> getAllDiffusions(Date date, Integer roomId) {
        return new ResponseEntity<>(diffusionRepo.findByDateAndRoom(date,roomId), HttpStatus.OK);
    }

    public ResponseEntity<String> addDiffusion(Diffusion diffusion) {
        var savedDiffusion = Diffusion.builder()
                .date(diffusion.getDate())
                .film(diffusion.getFilm())
                .room(diffusion.getRoom())
                .build();
        diffusionRepo.save(savedDiffusion);
        return new ResponseEntity<>("Added",HttpStatus.OK);
    }

    public ResponseEntity<String> updateDiffusion(Integer id) {
        // TODO add update logic
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDiffusion(Integer id) {
        var diffusionToDelete = diffusionRepo.findById(id).orElseThrow();
        diffusionRepo.delete(diffusionToDelete);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
