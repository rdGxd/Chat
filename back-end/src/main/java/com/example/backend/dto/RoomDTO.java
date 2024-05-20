package com.example.backend.dto;

import java.util.List;

public record RoomDTO(String id, String ownerId, String name, List<UserDTO> user, List<MessageDTO> message,
                      String createdAt, String updatedAt) {

}
