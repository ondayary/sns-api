package com.example.finalproject_leedaon.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {
    private Integer id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostCreateResponse toCreateResponse() {
        return new PostCreateResponse("포스트가 등록되었습니다.", this.id);
    }

    // 마이피드 조회시 사용
    public PostReadResponse toReadResponse() {
        return new PostReadResponse(this.id, this.title, this.body, this.userName, this.createdAt, this.lastModifiedAt);
    }
}