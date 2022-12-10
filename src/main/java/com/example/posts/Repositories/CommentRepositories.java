package com.example.posts.Repositories;

import com.example.posts.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepositories extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(Long id);

    List<Comment> findCommentByPostId(Long postId);
}
