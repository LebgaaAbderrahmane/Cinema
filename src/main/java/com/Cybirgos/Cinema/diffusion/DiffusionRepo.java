package com.Cybirgos.Cinema.diffusion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiffusionRepo extends JpaRepository<Diffusion,Integer> {
    @Query(value = "select * from Diffusion d where d.date =:date and d.room =:roomId",nativeQuery = true)
    List<Diffusion> findByDateAndRoom (Date date, Integer roomId);

    Optional<Diffusion> findById (Integer id);
}
