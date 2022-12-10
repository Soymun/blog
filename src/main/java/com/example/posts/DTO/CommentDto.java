package com.example.posts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private Long postId;

    private Long userId;

    private String text;

    private Long likes;

    private Long dislikes;

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
