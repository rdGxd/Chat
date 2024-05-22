package com.example.backend.service;

import com.example.backend.dto.RoomDTO;
import com.example.backend.model.Room;
import com.example.backend.model.user.User;
import com.example.backend.model.user.UserRole;
import com.example.backend.repository.RoomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final UserService userService;

    private final JdbcTemplate jdbcTemplate;

    public RoomService(RoomRepository roomRepository, UserService userService, JdbcTemplate jdbcTemplate) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(String id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public Room insert(Room room) {
        return roomRepository.save(room);
    }

    public void update(String id, String token, RoomDTO dto) {
        Room room = roomRepository.getReferenceById(id);
        User user = userService.findByToken(token);
        if (user.getRole() == UserRole.ADMIN || room.getOwnerId().equals(user.getId())) {
            updatedRoom(room, dto);
            roomRepository.save(room);
        }
    }

    private void updatedRoom(Room room, RoomDTO dto) {
        if (!dto.name().isBlank()) {
            room.setName(dto.name());
        }
    }

    public void delete(String id, String token) {
        Room room = findById(id);
        User user = userService.findByToken(token);
        if (user.getRole() == UserRole.ADMIN || room.getOwnerId().equals(user.getId())) {
            jdbcTemplate.update("DELETE FROM ROOM_USER WHERE ROOM_ID = ?", room.getId());
            jdbcTemplate.update("DELETE FROM ROOM_MESSAGES WHERE ROOM_ID = ?", room.getId());
            user.setRoom(null);
            roomRepository.delete(room);
        }
    }

    public void connect(String id, String token) {
        Room room = findById(id);
        User user = userService.findByToken(token);
        if (!room.getUser().contains(user)) {
            user.setRoom(room);
            room.getUser().add(user);
            roomRepository.save(room);
        }
    }

    public void disconnect(String id, String token) {
        Room room = findById(id);
        User user = userService.findByToken(token);
        if (room.getOwnerId().equals(user.getId())) {
            delete(id, token);
        }
        if (room.getUser().contains(user)) {
            user.setRoom(null);
            room.getUser().remove(user);
            roomRepository.save(room);
        }
    }
}
