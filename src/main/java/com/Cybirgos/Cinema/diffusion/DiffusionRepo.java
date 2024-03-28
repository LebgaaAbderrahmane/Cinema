package com.Cybirgos.Cinema.diffusion;

import com.Cybirgos.Cinema.film.Film;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiffusionRepo extends JpaRepository<Diffusion,Integer> {
    @Query(value = "select * from Diffusion d where d.date =:date and d.room_id =:roomId",nativeQuery = true)
    Optional<List<Diffusion>>  findByDateAndRoom (LocalDate date, Integer roomId);

    @NonNull Optional<Diffusion> findById (@NonNull Integer id);

    @Query(value = "select * from Diffusion d where d.room_id =:id",nativeQuery = true)
    Optional<List<Diffusion>>   findByRoomId(Integer id);

    @Query(value = "select * from Diffusion d where d.film_id =:id",nativeQuery = true)
    Optional<Film> findByFilmId(Integer id);
}
