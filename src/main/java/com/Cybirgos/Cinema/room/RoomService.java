package com.Cybirgos.Cinema.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepo roomRepo;
    public ResponseEntity<List<Room>> getAllRooms() {
        return new ResponseEntity<>(roomRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addRoom(Room room) {
        var savedRoom = Room.builder()
                .number(room.getNumber())
                .capacity(room.getCapacity())
                .nbVipSeats(room.getNbVipSeats())
                .build();
        roomRepo.save(savedRoom);
        return new ResponseEntity<>("Added",HttpStatus.OK);
    }

    public ResponseEntity<String> updateRoom(Integer id) {
        //TODO add update logic
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteRoom(Integer id) {
        var roomToDelete = roomRepo.findById(id).orElseThrow();
        roomRepo.delete(roomToDelete);
        return new ResponseEntity<>("Deleted ",HttpStatus.OK);
    }
}
