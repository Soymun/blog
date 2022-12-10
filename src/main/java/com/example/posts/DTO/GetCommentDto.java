package com.example.posts.DTO;

import lombok.Data;

@Data
public class GetCommentDto {

    private Long postId;

    private Long from;

    private Long to;
}
