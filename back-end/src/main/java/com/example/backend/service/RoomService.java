package com.example.backend.service;

import com.example.backend.dto.RoomDTO;
import com.example.backend.model.Room;
import com.example.backend.model.user.User;
import com.example.backend.model.user.UserRole;
import com.example.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static void updatedRoom(Room room, RoomDTO dto) {
        if (!dto.name().isBlank()) {
            room.setName(dto.name());
        }
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(String id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void insert(Room room) {
        roomRepository.save(room);
    }

    public void update(String id, String token, RoomDTO dto) {
        Room room = roomRepository.getReferenceById(id);
        User user = userService.findByToken(token);
        if (user.getRole() == UserRole.ADMIN || room.getOwnerId().equals(user.getId())) {
            updatedRoom(room, dto);
            roomRepository.save(room);
        }
    }

    public void delete(String id, String token) {
        Room room = findById(id);
        User user = userService.findByToken(token);
        if (user.getRole() == UserRole.ADMIN || room.getOwnerId().equals(user.getId())) {
            jdbcTemplate.update("DELETE FROM ROOM_USER WHERE ROOM_ID = ?", room.getId());
            user.setRoom(null);
            roomRepository.delete(room);
        }
    }
}
