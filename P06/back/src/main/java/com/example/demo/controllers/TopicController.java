package com.example.demo.controllers;


import com.example.demo.dtos.PostDto;
import com.example.demo.dtos.TopicDto;
import com.example.demo.mappers.TopicMapper;
import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.services.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for handling topic-related operations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;
    /**
     * Endpoint for retrieving a topic by its ID.
     *
     * @param id The ID of the topic to retrieve.
     * @return ResponseEntity containing the topic DTO if found, or a not found response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> findById(@PathVariable("id") String id) {
        try {
            Topic topic = this.topicService.findById(Long.valueOf(id));

            if (topic == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(this.topicMapper.toDto(topic));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for retrieving all topics.
     *
     * @return ResponseEntity containing a list of topic DTOs.
     */
    @GetMapping()
    public ResponseEntity<List<TopicDto>> findAll() {
        List<Topic> topics = this.topicService.findAll();

        List<TopicDto> topicDtos = new ArrayList<TopicDto>();

        for (Topic topic : topics) {
            topicDtos.add(this.topicMapper.toDto(topic));
        }
        return ResponseEntity.ok().body(topicDtos);
    }

    /**
     * Endpoint for creating a new topic.
     *
     * @param topicDto The DTO containing the details of the new topic.
     * @return ResponseEntity containing the created topic DTO.
     */
    @PostMapping()
    public ResponseEntity<TopicDto> create(@Valid @RequestBody TopicDto topicDto) {
        Topic topic = this.topicMapper.toEntity(topicDto);
        this.topicService.create(topic);
        return ResponseEntity.ok().body(this.topicMapper.toDto(topic));
    }
}
