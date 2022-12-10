package com.example.posts.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GetCommentByOrder {

    private Long postId;
    private List<PredicateDto> map;
}
