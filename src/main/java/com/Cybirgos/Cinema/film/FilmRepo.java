package com.Cybirgos.Cinema.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepo extends JpaRepository<Film,Integer> {
    Optional<Film> findById (Integer id);
}
