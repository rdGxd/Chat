package com.example.backend.model;

import com.example.backend.model.user.User;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

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
