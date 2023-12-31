package com.chatop.controllers;

import com.chatop.dtos.CreateRentalDto;
import com.chatop.dtos.MessageResponseDTO;
import com.chatop.dtos.RentalDto;
import com.chatop.dtos.UpdateRentalDto;
import com.chatop.entities.Rental;
import com.chatop.services.JWTService;
import com.chatop.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@RestController
public class RentalController {
    private final RentalService rentalService;
    private final JWTService jwtService;
    private final HttpServletRequest request;

    @Value("${image.storage.path}")
    private String localpath;

    public RentalController(RentalService rentalService, JWTService jwtService, HttpServletRequest request) {
        this.rentalService = rentalService;
        this.jwtService = jwtService;
        this.request = request;
    }

    @Operation(summary = "Add new rental")
    @PostMapping(value = "api/rentals", consumes = { "multipart/form-data" })
    public ResponseEntity<Rental> register(@ModelAttribute CreateRentalDto createRentalDto, @RequestHeader("Authorization") String bearerToken) throws IOException {
        String username = jwtService.returnuser(bearerToken);
        Rental newrental = rentalService.addRental(createRentalDto, username);
        return ResponseEntity.ok(newrental);
    }

    @Operation(summary = "Update existing rental")
    @PutMapping(value = "api/rentals/{rentalId}", consumes = {"*/*"})
    public ResponseEntity<MessageResponseDTO> updateRental(@ModelAttribute UpdateRentalDto updateRentalDto, @RequestHeader("Authorization") String bearerToken, @PathVariable(value="rentalId") Integer rentalId) throws IOException {
        String username = jwtService.returnuser(bearerToken);
        MessageResponseDTO message = rentalService.updateRental(updateRentalDto, username, rentalId);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Retrieve Rental")
    @GetMapping("api/rentals/{rentalId}")
    @ResponseBody
    public RentalDto getrental(@RequestHeader("Authorization") String bearerToken, @PathVariable(value="rentalId") Integer rentalId) {
        RentalDto newrental = rentalService.getRental(rentalId);
            return ResponseEntity.ok(newrental).getBody();
    }

    @Operation(summary = "Retrieve Image")
    @GetMapping(value = "image/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getimage(@PathVariable(value="imgName") String imgName) throws IOException {
        String path = localpath + imgName;
        InputStream in = new FileInputStream(path);
        return IOUtils.toByteArray(in);
    }

    @Operation(summary = "Retrieve All Rental")
    @GetMapping("api/rentals")
    @ResponseBody
    public ResponseEntity getallrental(@RequestHeader("Authorization") String bearerToken) {
        List<RentalDto>  rentalDtos = rentalService.allRental();
        return new ResponseEntity(Collections.singletonMap("rentals", rentalDtos), HttpStatus.OK);    }
}
