package com.example.backend.service;

import com.example.backend.dto.MessageDTO;
import com.example.backend.model.Message;
import com.example.backend.model.Room;
import com.example.backend.model.user.User;
import com.example.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    public void insert(Message message) {
        messageRepository.save(message);
    }

    public void sendMessage(String id, String token, MessageDTO message) {
        Room room = roomService.findById(id);
        User user = userService.findByToken(token);
        room.getUser().forEach(u -> {
            if (u.getId().equals(user.getId())) {
                Message newMessage = new Message(message.text(), user.getId(), user.getUsername());
                insert(newMessage);
                room.getMessages().add(newMessage);
            }
        });
        roomService.insert(room);
    }

    public List<Message> getMessage(String id, String token) {
        Room room = roomService.findById(id);
        User user = userService.findByToken(token);
        List<Message> messages = new ArrayList<>();
        room.getUser().forEach(u -> {
            if (u.getId().equals(user.getId())) {
                for (Message message : room.getMessages()) {
                    messages.add(new Message(message.getText(), message.getUserId(), user.getUsername()));
                }
            }
        });
        return messages;
    }
}
