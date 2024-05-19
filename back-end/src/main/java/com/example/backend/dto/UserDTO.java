package com.example.backend.dto;

import com.example.backend.model.user.UserRole;

public record UserDTO(String id, String email, UserRole role, String createdAt, String updatedAt) {

}
