package com.Cybirgos.Cinema.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepo extends JpaRepository<Image,Long>{
    Optional<Image> findById(Long id);

}
