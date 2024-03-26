package com.Cybirgos.Cinema.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Integer> {
    Seat findBySeatNb (Integer seatNb);
}
