package com.example.demo.mappers;


import com.example.demo.dtos.UserDto;
import com.example.demo.models.Post;
import com.example.demo.models.Topic;
import com.example.demo.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", imports = {Arrays.class, Post.class, Collectors.class, Topic.class, User.class, Collections.class, Optional.class})
public interface UserMapper extends EntityMapper<UserDto, User> {

}