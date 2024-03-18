package com.example.demo.controllers;

import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.LazyCommentDto;
import com.example.demo.mappers.CommentMapper;
import com.example.demo.models.Comment;
import com.example.demo.models.User;
import com.example.demo.payloads.requests.CommentRequest;
import com.example.demo.security.JWTService;
import com.example.demo.services.CommentService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling comment-related operations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final JWTService jwtService;

    /**
     * Endpoint for retrieving a comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return ResponseEntity containing the comment DTO if found, or a not found response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LazyCommentDto> findById(@PathVariable("id") String id) {
        try {
            LazyCommentDto comment = this.commentService.findById(Long.valueOf(id));

            if (comment == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(comment);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Endpoint for retrieving all comments.
     *
     * @return ResponseEntity containing a list of comment DTOs.
     */
    @GetMapping()
    public ResponseEntity<List<CommentDto>> findAll() {
        List<Comment> comments = this.commentService.findAll();

        List<CommentDto> commentDtos = new ArrayList<CommentDto>();

        for (Comment comment : comments) {
            commentDtos.add(this.commentMapper.toDto(comment));
        }
        return ResponseEntity.ok().body(commentDtos);
    }

    /**
     * Endpoint for creating a new comment.
     *
     * @param bearerToken   The JWT bearer token for authentication.
     * @param commentRequest The request object containing the comment details.
     * @return ResponseEntity containing the created comment DTO.
     */
    @PostMapping()
    public ResponseEntity<CommentDto> create(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody CommentRequest commentRequest) {
        String userEmail = this.jwtService.returnuser(bearerToken);
        User user = this.userService.findByEmail(userEmail);

        Comment comment = this.commentService.create(user, commentRequest);
        return ResponseEntity.ok().body(this.commentMapper.toDto(comment));
    }
}
