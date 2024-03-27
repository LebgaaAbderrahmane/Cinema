package com.Cybirgos.Cinema.room;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    @NonNull Optional<Room> findById (@NonNull Integer id);

    @Query(value = "SELECT * FROM room r WHERE r.room_number =:roomNumber",nativeQuery = true)
    Optional<Room>  findByRoomNumber(Integer roomNumber);
}
