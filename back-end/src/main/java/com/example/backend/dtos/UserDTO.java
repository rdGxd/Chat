package com.example.backend.dtos;

import com.example.backend.models.user.UserRole;

public record UserDTO(String id, String email, UserRole role, String createdAt, String updatedAt) {

}
