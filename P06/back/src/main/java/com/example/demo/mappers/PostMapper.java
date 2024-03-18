package com.example.demo.mappers;

import com.example.demo.dtos.PostDto;
import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.models.User;
import com.example.demo.services.TopicService;
import com.example.demo.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", imports = {Arrays.class, Collectors.class, Topic.class, User.class, Collections.class, Optional.class})
public interface  PostMapper extends EntityMapper<PostDto, Post> {

      PostDto toDto(Post post);
      List<PostDto> iterableToPostDtos(List<Post> posts);
}
