package com.Cybirgos.Cinema.diffusion;

import com.Cybirgos.Cinema.film.Film;
import com.Cybirgos.Cinema.film.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diffusion")
@CrossOrigin
@RequiredArgsConstructor
public class DiffusionController {
    @Autowired
    DiffusionService diffusionService;
    @GetMapping("/getAllDiffusions")
    public ResponseEntity<List<Diffusion>> getAllDiffusions (@RequestParam Date date ,@RequestParam Integer roomId){
        return diffusionService.getAllDiffusions(date,roomId);
    }
    @PostMapping("/addDiffusion")
    public ResponseEntity<String> addDiffusion(@RequestBody Diffusion diffusion){
        return diffusionService.addDiffusion(diffusion);
    }
    @PutMapping("/updateDiffusion/{id}")
    public ResponseEntity<String> updateDiffusion (@PathVariable Integer id,@RequestBody Diffusion diffusion){
        return diffusionService.updateDiffusion(id, diffusion);
    }
    @DeleteMapping("/deleteDiffusion/{id}")
    public ResponseEntity<String > deleteDiffusion (@PathVariable Integer id){
        return diffusionService.deleteDiffusion(id);
    }
}
