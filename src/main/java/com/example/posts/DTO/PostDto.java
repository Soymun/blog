package com.example.posts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String name;

    private Long userId;

    private String text;

    private Long likes;

    private Long dislikes;


    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
