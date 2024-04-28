package com.example.demo.controllers;

import com.example.demo.entities.Message;
import com.example.demo.entities.User;
import com.example.demo.payloads.MessageRequest;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest) {
        User sender = userRepository.findByUsername(messageRequest.getSender());
        if (sender == null) {
            sender = new User();
            sender.setUsername(messageRequest.getSender());
            userRepository.save(sender);
        }

        User receiver = userRepository.findByUsername(messageRequest.getReceiver());

        Message message = new Message();
        message.setContent(messageRequest.getMessage());
        message.setTimestamp(LocalDateTime.now());
        message.setReceiver(receiver);
        message.setSender(sender);

        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages/{username}")
    public ResponseEntity<?> getMessagesByUsername(@PathVariable String username) {
        User sender = userRepository.findByUsername(username);
        List<Message> messages = messageRepository.findBySender(sender);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/responses/{username}")
    public ResponseEntity<?> getAdminResponsesByUsername(@PathVariable String username) {
        User admin = userRepository.findByUsername("admin");
        User sender = userRepository.findByUsername(username);

        List<Message> responses = messageRepository.findBySenderAndReceiver(admin, sender);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
