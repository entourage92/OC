package com.example.demo.services;

import com.example.demo.dtos.PostDto;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    private final PostRepository postRepository;

    public Post findById(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    public Post create(Post post, String userEmail) {
        post.setPublished(new Date());
        post.setAuthor(userRepository.findByEmail(userEmail).orElse(null));
        return this.postRepository.save(post);
    }

    public void delete(Long id) {
        this.postRepository.deleteById(id);
    }

    public Post update(Long id, Post post) {
        post.setId(id);
        post.setUpdated(new Date());
        post.setAuthor(userRepository.findById(1L).orElse(null));
        return this.postRepository.save(post);
    }
}
