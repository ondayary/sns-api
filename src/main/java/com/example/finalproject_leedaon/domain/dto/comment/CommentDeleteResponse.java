package com.example.finalproject_leedaon.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteResponse {
    private String message;
    private Integer id;
}