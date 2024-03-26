package com.Cybirgos.Cinema.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepo extends JpaRepository<Film,Integer> {
    Optional<Film> findById (Integer id);
    @Query(value = "SELECT * FROM film f WHERE f.category =:category",nativeQuery = true)
    List<Film> findByCategory (String category);
    @Query(value = "SELECT * FROM film f WHERE f.version =:version",nativeQuery = true)
    List<Film> findByVersion (String version);
    @Query(value = "SELECT * FROM film f WHERE f.name LIKE %:name%",nativeQuery = true)
    List<Film> findByName (String name);
}
