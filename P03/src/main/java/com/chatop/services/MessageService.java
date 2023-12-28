package com.chatop.services;

import com.chatop.dtos.MessageDto;
import com.chatop.dtos.MessageResponseDTO;
import com.chatop.entities.Message;
import com.chatop.entities.Rental;
import com.chatop.entities.User;
import com.chatop.repository.MessageRepository;
import com.chatop.repository.RentalRepository;
import com.chatop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessageRepository messageRepository;

    public MessageService(UserRepository userRepository, RentalRepository rentalRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.messageRepository = messageRepository;
    }

    public MessageResponseDTO saveMessage(MessageDto messageDto) {
        User user = userRepository.findById(messageDto.getUser_id()).orElseThrow(() -> new NoSuchElementException("User not found"));
        Rental rental = rentalRepository.findById(messageDto.getRental_id()).orElseThrow(() -> new NoSuchElementException("Rental not found"));

        Message message = new Message();
        message.setUser(user);
        message.setRental(rental);
        message.setMessage(messageDto.getMessage());
        messageRepository.save(message);
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setMessage("Message send with success");
        return messageResponseDTO;
    }
}
