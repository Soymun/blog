package com.example.posts.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinAboutPost {
    public Long id;

    private String name;

    private Long userId;

    private Long likes;

    private Long dislikes;
}
