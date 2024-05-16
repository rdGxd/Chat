package com.example.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.RegisterDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.user.User;
import com.example.backend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll() {
    List<User> users = userService.findAll();
    List<UserDTO> usersDTO = new ArrayList<>();

    for (User u : users) {
      usersDTO.add(new UserDTO(u.getId(), u.getEmail(), u.getRole(), u.getCreatedAt(), u.getUpdatedAt()));
    }

    return ResponseEntity.ok().body(usersDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable String id) {
    User user = userService.findById(id);
    UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt(),
        user.getUpdatedAt());
    return ResponseEntity.ok().body(userDTO);
  }

  @PostMapping
  public ResponseEntity<Void> insert(@RequestBody @Validated RegisterDTO user) {
    userService.createUser(user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestHeader("Authorization") String token,
      @RequestBody RegisterDTO data) {
    userService.update(id, token, data);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {

    userService.delete(id, token);
    return ResponseEntity.ok().build();
  }
}
