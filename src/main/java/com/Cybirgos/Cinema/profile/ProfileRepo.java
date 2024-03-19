package com.Cybirgos.Cinema.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile,Integer> {
    @Query(value = "SELECT * FROM profile s WHERE s.username =:username",nativeQuery = true)
    Optional<Profile> findByUsername (String username);
}
