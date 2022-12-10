package com.example.posts.Mappers;

import com.example.posts.DTO.PostDto;
import com.example.posts.Entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post postDtoToPost(PostDto postDto);

    PostDto postToPostDto(Post post);
}
