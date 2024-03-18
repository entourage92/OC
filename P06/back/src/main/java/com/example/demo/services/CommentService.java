package com.example.demo.services;

import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.LazyCommentDto;
import com.example.demo.mappers.CommentMapper;
import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.models.User;
import com.example.demo.payloads.requests.CommentRequest;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Transactional
    public LazyCommentDto findById(Long id) throws Exception {

        Comment comment = commentRepository.findById(1L).orElse(null);
        String debug = comment.getAuthor().getEmail();
        LazyCommentDto commentDtos = commentRepository.findCommentValuesById(id);

        return commentDtos;
    }

    public Comment create(User user, CommentRequest commentRequest) {
        Post post = this.postService.findById(Long.valueOf(commentRequest.getPostId()));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(commentRequest.getContent());
        comment.setPublished(new Date());
        comment.setAuthor(user);
        return this.commentRepository.save(comment);
    }
}
