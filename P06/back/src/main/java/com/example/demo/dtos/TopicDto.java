package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TopicDto {
    private Long id;
    private String name;
    private Set<PostDto> postDtos;
    private String description;

    public TopicDto(String name, Set<PostDto> postDtos, String description) {
        this.name = name;
        this.postDtos = postDtos;
        this.description = description;
    }
}