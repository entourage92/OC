package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class PostDto {
    private Long id;
    private UserDto author;
    private String title;
    private Date published;
    private Date updated;
    private List<TopicDto> topics;
    private String content;
    private List<CommentDto> comments;

    public PostDto(UserDto author, String title, Date published, Date updated, List<TopicDto> topics, String content, List<CommentDto> comments) {
        this.author = author;
        this.title = title;
        this.published = published;
        this.updated = updated;
        this.topics = topics;
        this.content = content;
        this.comments = comments;
    }
}
