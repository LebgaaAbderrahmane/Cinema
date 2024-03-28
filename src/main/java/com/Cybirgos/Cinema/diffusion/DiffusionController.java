package com.Cybirgos.Cinema.diffusion;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diffusion")
@CrossOrigin
@RequiredArgsConstructor
public class DiffusionController {
    @Autowired
    DiffusionService diffusionService;
    @GetMapping("/getDiffusionsByDateAndRoom")
    public ResponseEntity<List<Diffusion>> getDiffusionsByDateAndRoom (@RequestParam LocalDate date , @RequestParam Integer roomNumber){
        return diffusionService.getDiffusionsByDateAndRoom(date,roomNumber);
    }
    @GetMapping("/isDiffusionFull/{id}")
    public ResponseEntity<Boolean> isDiffusionFull (@PathVariable Integer id){
        return diffusionService.isDiffusionFull(id);
    }
    @GetMapping("/getAllDiffusions")
    public ResponseEntity<List<Diffusion>> getAllDiffusions (){
        return diffusionService.getAllDiffusions();
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
