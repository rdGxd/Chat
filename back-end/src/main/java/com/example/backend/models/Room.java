package com.example.backend.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.backend.models.user.User;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NonNull
  private String name;

  @NonNull
  private String ownerId;

  @CreationTimestamp
  private String createdAt;

  @UpdateTimestamp
  private String updatedAt;

  @OneToMany
  private List<User> user = new ArrayList<>();

  public Room(String name, User user) {
    this.name = name;
    this.user.add(user);
    this.ownerId = user.getId();
  }

}
