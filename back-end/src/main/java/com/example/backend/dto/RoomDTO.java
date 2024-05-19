package com.example.backend.dto;

import java.util.List;

public record RoomDTO(String id, String ownerId, String name, List<UserDTO> user, String createdAt, String updatedAt) {

}
