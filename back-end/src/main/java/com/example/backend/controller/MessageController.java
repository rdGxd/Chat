package com.example.backend.controller;

import com.example.backend.dto.MessageDTO;
import com.example.backend.model.Message;
import com.example.backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> sendMessage(@PathVariable String id, @RequestHeader("Authorization") String token, @RequestBody MessageDTO message) {
        messageService.sendMessage(id, token, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageDTO>> getMessage(@PathVariable String id, @RequestHeader("Authorization") String token) {
        List<Message> messages = messageService.getMessage(id, token);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (Message message : messages) {
            messageDTOS.add(new MessageDTO(message.getId(), message.getText(), message.getUserName(), message.getUserId()));
        }
        return ResponseEntity.ok().body(messageDTOS);
    }
}
