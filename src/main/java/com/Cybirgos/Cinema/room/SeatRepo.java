package com.Cybirgos.Cinema.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Integer> {
    Seat findBySeatNb (Integer seatNb);
    //List<Seat> getAvailableSeats (Integer id);
    @Query(value = "SELECT * FROM seat s WHERE s.seat_number =:seatNb and s.room_id =:roomId",nativeQuery = true)
    Seat findBySeatNbAndRoomId(Integer seatNb,Integer roomId);

    @Query(value = "DELETE * FROM seat s WHERE s.room_id =:roomId",nativeQuery = true)
    void deleteByRoomId(Integer id);

    @Query(value = "SELECT * FROM seat s WHERE s.room_id =:roomId",nativeQuery = true)
    List<Seat> findByRoomId(Integer id);
}
