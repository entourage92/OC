package com.example.demo.repository;

import com.example.demo.dtos.CommentDto;
import com.example.demo.dtos.LazyCommentDto;
import com.example.demo.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE id = ?1", nativeQuery = true)
    LazyCommentDto findCommentValuesById(Long id);
}
