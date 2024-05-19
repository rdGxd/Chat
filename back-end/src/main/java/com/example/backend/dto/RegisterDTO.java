package com.example.backend.dto;

import com.example.backend.model.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
