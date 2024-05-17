package com.example.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.RoomDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.Room;
import com.example.backend.models.user.User;
import com.example.backend.services.RoomService;
import com.example.backend.services.UserService;

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

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {
    roomService.delete(id, token);
    return ResponseEntity.ok().build();
  }
}
