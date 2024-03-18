package com.example.demo.dtos;

import com.example.demo.models.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public interface LazyCommentDto {
    Long getId();
    String getContent();
    Date getPublished();
    Long getAuthor();
    Long getPost();
}
