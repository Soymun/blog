package com.example.posts.Mappers;

import com.example.posts.DTO.CommentDto;
import com.example.posts.Entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMappers {

    Comment commentDtoTpComment(CommentDto commentDto);

    CommentDto commentToCommentDto(Comment comment);
}
