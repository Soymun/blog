package com.example.posts.Repositories;

import com.example.posts.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepositories extends JpaRepository<Post, Long> {

    Optional<Post> findPostById(Long id);

    List<Post> findPostByUserId(Long userId);
}
