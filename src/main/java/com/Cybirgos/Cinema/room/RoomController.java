package com.Cybirgos.Cinema.room;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@CrossOrigin
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    RoomService roomService;
    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Room>> getAllRooms (){
        return roomService.getAllRooms();
    }
    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody Room room){
        return roomService.addRoom(room);
    }
    @PutMapping("/updateRoom/{id}")
    public ResponseEntity<String> updateRoom (@PathVariable Integer id){
        return roomService.updateRoom(id);
    }
    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<String > deleteRoom (@PathVariable Integer id){
        return roomService.deleteRoom(id);
    }
}
