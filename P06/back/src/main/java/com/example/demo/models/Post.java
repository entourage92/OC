package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    private String title;
    @Column(name = "published", updatable = false)
    private Date published;
    private Date updated;
    @Column(columnDefinition="TEXT")
    private String content;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_topic",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private List<Topic> topics;
    @OneToMany(mappedBy="post")
    private List<Comment> comments;

    public Post(User author, String title, Date published, Date updated, List <Topic> topics, String content, List<Comment> comments) {
        this.author = author;
        this.title = title;
        this.published = published;
        this.updated = updated;
        this.topics = topics;
        this.content = content;
        this.comments = comments;
    }
}
