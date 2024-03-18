package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    public Topic findById(Long id) {
        return this.topicRepository.findById(id).orElse(null);
    }

    public Topic create(Topic topic) {
        return this.topicRepository.save(topic);
    }
}
