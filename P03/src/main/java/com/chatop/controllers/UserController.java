package com.chatop.controllers;

import com.nimbusds.oauth2.sdk.GeneralException;
import com.nimbusds.oauth2.sdk.ParseException;
import com.chatop.dtos.LoginUserDto;
import com.chatop.dtos.RegisterUserDto;
import com.chatop.dtos.UserDto;
import com.chatop.entities.User;
import com.chatop.repository.UserRepository;
import com.chatop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatop.services.JWTService;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {

    private final JWTService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(JWTService jwtService, UserService userService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "User login")
    @PostMapping(value = "api/auth/login", consumes = {"*/*"})
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginUserDto loginUserDto) {
        if (userService.authenticate(loginUserDto)) {
            String token = jwtService.generateToken(loginUserDto);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.ok(Collections.singletonMap("error", "Check credentials"));
    }

    @Operation(summary = "Retrieve Current User")
    @GetMapping("api/auth/me")
    public UserDto authenticatedUser(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.returnuser(bearerToken);
        User newuser = userRepository.findByUsername(username);
        UserDto user = userService.getUser(newuser.getId());
        return ResponseEntity.ok(user).getBody();
    }

    @Operation(summary = "User Register")
    @PostMapping(value = "api/auth/register", consumes = {"*/*"})
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterUserDto registerUserDto) throws GeneralException, ParseException {
        User newuser = userService.addUser(registerUserDto);
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(registerUserDto.getEmail());
        loginUserDto.setPassword(registerUserDto.getPassword());
        String token = jwtService.generateToken(loginUserDto);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @Operation(summary = "Retrieve User By ID")
    @GetMapping("api/user/{userId}")
    @ResponseBody
    public UserDto getUser(@RequestHeader("Authorization") String bearerToken, @PathVariable(value="userId") Integer userId) {
        UserDto newuser = userService.getUser(userId);
        return ResponseEntity.ok(newuser).getBody();
    }
}