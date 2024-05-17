package com.example.backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

}
