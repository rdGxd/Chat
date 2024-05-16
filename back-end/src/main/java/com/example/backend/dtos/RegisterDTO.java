package com.example.backend.dtos;

import com.example.backend.models.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
