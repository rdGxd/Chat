package com.example.backend.controller;

import com.example.backend.dto.RoomDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.Room;
import com.example.backend.model.user.User;
import com.example.backend.service.RoomService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        List<Room> rooms = roomService.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();

        for (Room r : rooms) {
            List<UserDTO> userDTOs = new ArrayList<>();
            for (User u : r.getUser()) {
                userDTOs.add(new UserDTO(u.getId(), u.getEmail(), u.getRole(), u.getCreatedAt(), u.getUpdatedAt()));
            }

            roomDTOs.add(new RoomDTO(r.getId(), r.getOwnerId(), r.getName(), userDTOs, r.getCreatedAt(), r.getUpdatedAt()));
        }
        return ResponseEntity.ok().body(roomDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable String id) {
        Room room = roomService.findById(id);
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User u : room.getUser()) {
            userDTOs.add(new UserDTO(u.getId(), u.getEmail(), u.getRole(), u.getCreatedAt(), u.getUpdatedAt()));
        }

        RoomDTO roomDTO = new RoomDTO(room.getId(), room.getOwnerId(), room.getName(), userDTOs, room.getCreatedAt(),
                room.getUpdatedAt());

        return ResponseEntity.ok().body(roomDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody RoomDTO dto, @RequestHeader("Authorization") String token) {
        User user = userService.findByToken(token);
        Room room = new Room(dto.name(), user);
        user.setRoom(room);
        roomService.insert(room);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestHeader("Authorization") String token, @RequestBody RoomDTO dto) {
        roomService.update(id, token, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {
        roomService.delete(id, token);
        return ResponseEntity.ok().build();
    }
}
