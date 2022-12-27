package com.example.finalproject_leedaon.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {
    private Integer id;
    private String title;
    private String body;

    public PostCreateResponse toCreateResponse() {
        return new PostCreateResponse("포스트가 등록되었습니다.", this.id);
    }
}