package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NonNull
    private String userId;

    @NonNull
    private String userName;

    @NonNull
    private String text;
    
    @CreationTimestamp
    private String createdAt;

    public Message(@NonNull String text, @NonNull String userId, @NonNull String userName) {
        this.text = text;
        this.userId = userId;
        this.userName = userName;
    }
}
