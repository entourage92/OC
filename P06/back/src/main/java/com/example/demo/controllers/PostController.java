package com.example.demo.controllers;

import com.example.demo.dtos.PostDto;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.security.JWTService;
import com.example.demo.services.PostService;
import com.example.demo.mappers.PostMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling operations related to posts.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostMapper postMapper;
    private final PostService postService;
    private final JWTService jwtService;

    /**
     * Constructor for the PostController.
     * @param postMapper The mapper for mapping between Post and PostDto.
     * @param postService The service for handling post-related operations.
     * @param jwtService The service for handling JWT operations.
     */
    public PostController(PostMapper postMapper, PostService postService, JWTService jwtService) {
        this.postMapper = postMapper;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint for retrieving a post by its ID.
     * @param id The ID of the post to retrieve.
     * @return ResponseEntity containing the post DTO if found, or a not found response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable("id") String id) {
        try {
            Post post = this.postService.findById(Long.valueOf(id));

            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(this.postMapper.toDto(post));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for retrieving all posts.
     * @return ResponseEntity containing a list of post DTOs.
     */
    @GetMapping()
    public ResponseEntity<List<PostDto>> findAll() {

        List<Post> posts = this.postService.findAll();

        List<PostDto> postDtos = new ArrayList<PostDto>();

        for (Post post : posts) {
            postDtos.add(this.postMapper.toDto(post));
        }

        return ResponseEntity.ok().body(postDtos);
    }

    /**
     * Endpoint for updating a post.
     * @param id The ID of the post to update.
     * @param postDto The DTO containing the updated post information.
     * @return ResponseEntity containing the updated post DTO.
     */
    @PutMapping("{id}")
    public ResponseEntity<PostDto> update(@PathVariable("id") String id, @Valid @RequestBody PostDto postDto) {
        try {
            Post post = this.postService.update(Long.parseLong(id), this.postMapper.toEntity(postDto));

            return ResponseEntity.ok().body(this.postMapper.toDto(post));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for deleting a post by its ID.
     * @param id The ID of the post to delete.
     * @return ResponseEntity containing a message indicating the status of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        try {
            Post post = this.postService.findById(Long.valueOf(id));
            if (post == null)
                return ResponseEntity.notFound().build();
            this.postService.delete(Long.valueOf(id));
            return ResponseEntity.ok().body("The post was deleted");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for creating a new post.
     * @param bearerToken The JWT bearer token for authentication.
     * @param postDto The DTO containing the new post information.
     * @return ResponseEntity containing the created post DTO.
     */
    @PostMapping()
    public ResponseEntity<PostDto> create(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody PostDto postDto) {
        Post post = this.postMapper.toEntity(postDto);
        String usermail = this.jwtService.returnuser(bearerToken);
        this.postService.create(post, usermail);
        return ResponseEntity.ok().body(this.postMapper.toDto(post));
    }
}
