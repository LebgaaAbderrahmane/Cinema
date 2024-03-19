package com.Cybirgos.Cinema.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    Optional<Room> findById (Integer id);
}
