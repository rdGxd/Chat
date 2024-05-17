package com.example.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.configs.security.TokenService;
import com.example.backend.dtos.RegisterDTO;
import com.example.backend.models.user.User;
import com.example.backend.models.user.UserRole;
import com.example.backend.repositorys.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  public User insert(User user) {
    return userRepository.save(user);
  }

  public void update(String id, String token, RegisterDTO data) {
    User userToken = findByToken(token);
    User userId = userRepository.getReferenceById(id);

    if (userToken.getId().equals(userId.getId())) {
      updatedUser(userId, data);
      userRepository.save(userId);
    }
  }

  private void updatedUser(User user, RegisterDTO userDTO) {
    if (userDTO.email() != null && userDTO.email().isBlank() == false) {
      user.setEmail(userDTO.email());
    }

    if (userDTO.password().length() >= 6 && userDTO.password().length() <= 16) {
      String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
      user.setPassword(encryptedPassword);
    }
  }

  public void delete(String id, String token) {
    User userToken = findByToken(token);
    User userId = userRepository.getReferenceById(id);
    if (userToken.getId().equals(userId.getId()) || userToken.getRole() == UserRole.ADMIN) {
      userRepository.delete(userId);
    }
  }

  public User createUser(RegisterDTO data) {
    Optional<User> user = userRepository.findByEmail(data.email());

    if (user.isPresent()) {
      throw new RuntimeException("Conta já existe!");
    }

    if (data.email().length() < 3 || data.email().length() > 16 || data.password().length() < 6
        || data.password().length() > 16) {
      throw new RuntimeException("DEU RUIM 3");
    }

    try {
      String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
      UserRole role = UserRole.USER;
      if (data.role() != null) {
        role = data.role();
      }

      User newUser = new User(data.email(), encryptedPassword, role);
      return insert(newUser);
    } catch (RuntimeException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public User findByToken(String token) {
    token = token.replace("Bearer ", "");
    String username = tokenService.validateToken(token);
    return findByEmail(username);
  }
}
