package com.example.backend.controller;

import com.example.backend.dto.RegisterDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.user.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
