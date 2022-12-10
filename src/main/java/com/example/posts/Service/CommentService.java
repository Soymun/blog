package com.example.posts.Service;

import com.example.posts.DTO.CommentDto;
import com.example.posts.DTO.GetCommentDto;
import com.example.posts.DTO.PredicateDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getComment(GetCommentDto commentDto);

    List<CommentDto> getCommentByPredicate(Long postId,List<PredicateDto> map);

    String saveComment(CommentDto commentDto);

    CommentDto patchComment(CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    void deleteComment(Long id);
}
