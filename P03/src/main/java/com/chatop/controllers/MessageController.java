package com.chatop.controllers;

import com.chatop.dtos.MessageDto;
import com.chatop.dtos.MessageResponseDTO;
import com.chatop.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "api/messages", method = RequestMethod.POST)
    public MessageResponseDTO message(@RequestBody MessageDto messageDto) {
        MessageResponseDTO messageResponseDTO = messageService.saveMessage(messageDto);

        return ResponseEntity.ok(messageResponseDTO).getBody();
    }

}
