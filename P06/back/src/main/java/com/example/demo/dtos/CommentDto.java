package com.example.demo.dtos;

import com.example.demo.models.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private UserDto author;
    private Date published;
    private String content;
    public CommentDto(Long id, UserDto author, Date published, String content) {
        this.id = id;
        this.author = author;
        this.published = published;
        this.content = content;
    }
}
