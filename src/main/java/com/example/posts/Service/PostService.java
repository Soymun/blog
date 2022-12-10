package com.example.posts.Service;

import com.example.posts.DTO.MinAboutPost;
import com.example.posts.DTO.PostDto;
import com.example.posts.DTO.PredicateDto;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostDto getPostById(Long id);

    String savePost(PostDto postDto);

    PostDto updatePost(PostDto postDto);

    PostDto patchPost(PostDto postDto);

    void deletePost(Long id);

    List<MinAboutPost> getAllPost();

    List<MinAboutPost> getPostByPredicate(List<PredicateDto> predicateDtos);
}
