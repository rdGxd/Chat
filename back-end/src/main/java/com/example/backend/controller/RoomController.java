package com.example.backend.controller;

import com.example.backend.dto.MessageDTO;
import com.example.backend.dto.RoomDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.Message;
import com.example.backend.model.Room;
import com.example.backend.model.user.User;
import com.example.backend.service.RoomService;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    private final UserService userService;

    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        List<Room> rooms = roomService.findAll();
        List<RoomDTO> roomDTOs = new ArrayList<>();
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Room r : rooms) {
            List<UserDTO> userDTOs = new ArrayList<>();
            getUsers(messageDTOs, r, userDTOs);

            roomDTOs.add(new RoomDTO(r.getId(), r.getOwnerId(), r.getName(), userDTOs, messageDTOs, r.getCreatedAt(), r.getUpdatedAt()));
        }
        return ResponseEntity.ok().body(roomDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable String id) {
        Room room = roomService.findById(id);
        List<UserDTO> userDTOs = new ArrayList<>();
        List<MessageDTO> messageDTOs = new ArrayList<>();

        getUsers(messageDTOs, room, userDTOs);

        RoomDTO roomDTO = new RoomDTO(room.getId(), room.getOwnerId(), room.getName(), userDTOs, messageDTOs, room.getCreatedAt(),
                room.getUpdatedAt());

        return ResponseEntity.ok().body(roomDTO);
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody RoomDTO dto, @RequestHeader("Authorization") String token) {
        User user = userService.findByToken(token);
        Room room = new Room(dto.name(), user);
        user.setRoom(room);
        Room r = roomService.insert(room);

        return ResponseEntity.ok().body(r.getId());
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

    @PostMapping("/{id}/connect")
    public ResponseEntity<Void> connect(@PathVariable String id, @RequestHeader("Authorization") String token) {
        roomService.connect(id, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disconnect")
    public ResponseEntity<Void> disconnect(@PathVariable String id, @RequestHeader("Authorization") String token) {
        roomService.disconnect(id, token);
        return ResponseEntity.ok().build();
    }


    private void getUsers(List<MessageDTO> messageDTOs, Room r, List<UserDTO> userDTOs) {
        for (User u : r.getUser()) {
            userDTOs.add(new UserDTO(u.getId(), u.getEmail(), u.getRole(), u.getCreatedAt(), u.getUpdatedAt()));
        }

        for (Message m : r.getMessages()) {
            messageDTOs.add(new MessageDTO(m.getId(), m.getText(), m.getUserName(), m.getUserId()));
        }
    }
}
