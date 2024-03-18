package com.example.demo.mappers;

import com.example.demo.dtos.CommentDto;
import com.example.demo.models.Comment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDto, Comment> {

}