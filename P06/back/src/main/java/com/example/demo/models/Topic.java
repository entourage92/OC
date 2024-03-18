package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;
    @ManyToMany(mappedBy = "topics", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Post> posts;

    public Topic(String name,List<Post> posts, String description) {
        this.name = name;
        this.posts = posts;
        this.description = description;
    }
}
