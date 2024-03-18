package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.payloads.requests.LoginRequest;
import com.example.demo.payloads.requests.SignupRequest;
import com.example.demo.payloads.responses.MessageResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Controller for handling authentication requests.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;


    /**
     * Endpoint for registering a new user.
     *
     * @param signUpRequest The signup request containing user details.
     * @return ResponseEntity with a success message if registration is successful,
     * or a bad request response if the email is already taken.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        this.userService.addUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Endpoint for authenticating a user.
     *
     * @param loginRequest The login request containing user credentials.
     * @return ResponseEntity with a JWT token if authentication is successful,
     * or a response with an error message if the credentials are incorrect.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (userService.authenticate(loginRequest)) {
            String token = jwtService.generateToken(loginRequest);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        return ResponseEntity.ok(Collections.singletonMap("error", "Check credentials"));
    }

}
