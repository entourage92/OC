package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.Topic;
import com.example.demo.models.User;
import com.example.demo.payloads.requests.LoginRequest;
import com.example.demo.security.JWTService;
import com.example.demo.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Controller for handling user-related operations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final JWTService jwtService;

    /**
     * Endpoint for retrieving a user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the user DTO if found, or a not found response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));

            if (user == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for updating a user's profile.
     *
     * @param bearerToken  The bearer token for authentication.
     * @param updateRequest The request containing the updated user information.
     * @return ResponseEntity containing a token if the update is successful, or a not found response if the user is not found.
     */
    @PostMapping("")
    public ResponseEntity<Map<String, String>> userprofile(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody LoginRequest updateRequest) {
        try {
            String userEmail = this.jwtService.returnuser(bearerToken);
            User user = this.userService.findByEmail(userEmail);
            String token = this.userService.update(user, updateRequest);

            if (token == null || user == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for retrieving the profile of the authenticated user.
     *
     * @param bearerToken The bearer token for authentication.
     * @return ResponseEntity containing the user DTO if found, or a not found response if not found.
     */
    @GetMapping("")
    public ResponseEntity<UserDto> userprofile(@RequestHeader("Authorization") String bearerToken) {
        try {
            String usermail = this.jwtService.returnuser(bearerToken);
            User user = this.userService.findByEmail(usermail);

            if (user == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for subscribing to a topic.
     *
     * @param bearerToken The bearer token for authentication.
     * @param topicId     The ID of the topic to subscribe to.
     * @return ResponseEntity containing a success message if the subscription is successful, or a not found response if the user or topic is not found.
     */
    @GetMapping("/subscribe/{topicId}")
    @Transactional
    public ResponseEntity<Map<String, String>> topicSubscribe(@RequestHeader("Authorization") String bearerToken, @PathVariable("topicId") String topicId) {
        try {
            String usermail = this.jwtService.returnuser(bearerToken);
            User user = this.userService.findByEmail(usermail);
            Topic topic = this.userService.subscribe(user, Long.valueOf(topicId));
            if (user == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(Collections.singletonMap("subscribed to ", topic.getName()));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for unsubscribing from a topic.
     *
     * @param bearerToken The bearer token for authentication.
     * @param topicId     The ID of the topic to unsubscribe from.
     * @return ResponseEntity containing a success message if the unsubscription is successful, or a not found response if the user or topic is not found.
     */
    @GetMapping("/unsubscribe/{topicId}")
    @Transactional
    public ResponseEntity<Map<String, String>> topicUnsubscribe(@RequestHeader("Authorization") String bearerToken, @PathVariable("topicId") String topicId) {
        try {
            String usermail = this.jwtService.returnuser(bearerToken);
            User user = this.userService.findByEmail(usermail);
            Topic topic = this.userService.unsubscribe(user, Long.valueOf(topicId));
            if (user == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(Collections.singletonMap("unsubscribed to ", topic.getName()));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
