package com.chatop.services;

import com.chatop.dtos.CreateRentalDto;

import com.chatop.dtos.MessageResponseDTO;
import com.chatop.dtos.RentalDto;
import com.chatop.dtos.UpdateRentalDto;
import com.chatop.entities.Rental;
import com.chatop.entities.User;
import com.chatop.repository.RentalRepository;
import com.chatop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@ComponentScan
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${image.storage.path}")
    private String localpath;

    public RentalService(RentalRepository RentalRepo, UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.rentalRepository = RentalRepo;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Rental addRental(CreateRentalDto input, String username) throws IOException {
        User u = userRepository.findByUsername(username);

        MultipartFile picture = input.getPicture();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
        Path path = Paths.get(localpath + fileName);
        Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = serverUrl + "/image/" + fileName;
        var rental = new Rental()
                .setCreatedAt(new Date())
                .setDescription(input.getDescription())
                .setPicture(imageUrl)
                .setName(input.getName())
                .setPrice(input.getPrice())
                .setSurface(input.getSurface())
                .setUser(u);

        return rentalRepository.save(rental);
    }

    public MessageResponseDTO updateRental(UpdateRentalDto input, String username, Integer rentalID) throws IOException {
        User u = userRepository.findByUsername(username);
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        Optional<Rental> dbrental = rentalRepository.findById(rentalID);

        if(dbrental.isEmpty()){
            messageResponseDTO.setMessage("Rental not updated error");
            return messageResponseDTO;
        }
        if(!Objects.equals(dbrental.get().getUser().getId(), u.getId())){
            messageResponseDTO.setMessage("Rental not updated error");
            return messageResponseDTO;
        }
        Rental newrental = dbrental.get();

        newrental.setName(input.getName());
        newrental.setSurface(input.getSurface());
        newrental.setPrice(input.getPrice());
        newrental.setDescription(input.getDescription());
        newrental.setUpdatedAt(new Date());
        rentalRepository.save(newrental);
        messageResponseDTO.setMessage("Rental updated !");
        return messageResponseDTO;
    }

    public RentalDto getRental(Integer id) {
        Optional<Rental> newrental = rentalRepository.findById(id);
        return newrental.map(rental -> {
            RentalDto response = modelMapper.map(rental, RentalDto.class);
            response.setOwner_id(rental.getUser().getId());
            return response;
        }).orElse(null);
    }

    public List<RentalDto> allRental() {
        List<Rental> rentals = rentalRepository.findAll();
        List<RentalDto> rentalDtos = rentals.stream().map(rental -> {
            RentalDto rentalDto = modelMapper.map(rental, RentalDto.class);
            rentalDto.setOwner_id(rental.getUser().getId());
            return rentalDto;
        }).toList();
        return rentalDtos;
    }
}
