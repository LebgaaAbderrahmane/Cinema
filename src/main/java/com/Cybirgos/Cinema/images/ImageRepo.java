package com.Cybirgos.Cinema.images;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepo extends JpaRepository<Image,Long>{
    @NonNull
    Optional<Image> findById(@NonNull Long id);

}
