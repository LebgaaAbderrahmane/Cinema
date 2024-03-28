package com.Cybirgos.Cinema.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    SeatRepo seatRepo;
    public ResponseEntity<List<Room>> getAllRooms() {
        return new ResponseEntity<>(roomRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> addRoom(Room room) {
        List<Seat> seats = new ArrayList<>();
        for(int i = 1; i<=room.getCapacity();i++){
            var seat = Seat.builder()
                    .seatNumber(i)
                    .isVip(false)
                    .isReserved(false)
                    .room(room)
                    .build();
            seats.add(seat);
        }
        var savedRoom = Room.builder()
                .roomNumber(room.getRoomNumber())
                .capacity(room.getCapacity())
                .isOccupied(false)
                .seats(seats)
                .build();
        roomRepo.save(savedRoom);
        return new ResponseEntity<>("Added",HttpStatus.OK);
    }

    public ResponseEntity<String> updateRoom(Integer id, Room room) {
        var updatedRoom = roomRepo.findById(id).orElseThrow();
        if (!updatedRoom.isOccupied()) {
            var previousCapacity = updatedRoom.getCapacity();
            List<Seat> seats = updatedRoom.getSeats();
            if (previousCapacity < room.getCapacity()) {
                for (int i = previousCapacity + 1; i <= room.getCapacity(); i++) {
                    var seat = Seat.builder()
                            .seatNumber(i)
                            .isVip(false)
                            .isReserved(false)
                            .room(room)
                            .build();
                    seats.add(seat);
                }
            } else if (previousCapacity > room.getCapacity()) {
                for (int i = previousCapacity + 1; i <= room.getCapacity(); i++) {
                    var seat = seatRepo.findBySeatNumberAndRoomId(i, id);
                    seats.remove(seat);
                }
            }
            updatedRoom.setSeats(seats);
            updatedRoom.setDiffusions(room.getDiffusions());
            updatedRoom.setRoomNumber(room.getRoomNumber());
            roomRepo.save(updatedRoom);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can't update an occupied room",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteRoom(Integer id) {
        var room = roomRepo.findById(id).orElseThrow();
        if(!room.isOccupied()) {
            seatRepo.deleteByRoomId(id);
            roomRepo.deleteById(id);
            return new ResponseEntity<>("Deleted ", HttpStatus.OK);
        }
        return new ResponseEntity<>("Can't delete an occupied room",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Seat>> getAllSeats(Integer id) {
        return new ResponseEntity<>(seatRepo.findByRoomId(id),HttpStatus.OK);
    }


    public ResponseEntity<Room> getRoomById(Integer id) {
        return new ResponseEntity<>(roomRepo.findById(id).orElseThrow(),HttpStatus.OK);
    }

    public ResponseEntity<String> isRoomNumberAvailable(Integer roomNumber) {
        if(roomRepo.findByRoomNumber(roomNumber).isPresent()){
            return new ResponseEntity<>("already used",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("available" ,HttpStatus.OK);
    }
}
