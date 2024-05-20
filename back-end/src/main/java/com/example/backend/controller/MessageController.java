package com.example.backend.controller;

import com.example.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> sendMessage(@PathVariable String id, @RequestHeader("Authorization") String token, @RequestBody String message) {
        messageService.sendMessage(id, token, message);
        return ResponseEntity.ok().build();
    }
}
