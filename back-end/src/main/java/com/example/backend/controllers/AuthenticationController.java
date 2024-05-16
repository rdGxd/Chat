package com.example.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.configs.security.TokenService;
import com.example.backend.dtos.AuthenticationDTO;
import com.example.backend.dtos.LoginResponseDTO;
import com.example.backend.dtos.RegisterDTO;
import com.example.backend.models.user.User;
import com.example.backend.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  private final UserService userService;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  public AuthenticationController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.tokenService = tokenService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
    User user = userService.findByEmail(data.email());
    if (passwordEncoder.matches(data.password(), user.getPassword())) {
      String token = tokenService.generateToken(user);
      return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Validated RegisterDTO data) {
    userService.createUser(data);
    return ResponseEntity.ok().build();
  }
}
