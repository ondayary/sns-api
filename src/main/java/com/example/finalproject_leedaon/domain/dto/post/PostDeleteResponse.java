package com.example.finalproject_leedaon.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDeleteResponse {
    private String message;
    private Integer postId;
}