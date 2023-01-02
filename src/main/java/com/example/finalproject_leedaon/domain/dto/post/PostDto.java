package com.example.finalproject_leedaon.domain.dto.post;

import com.example.finalproject_leedaon.domain.dto.post.PostCreateResponse;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    public PostCreateResponse toCreateResponse() {
        return new PostCreateResponse("포스트가 등록되었습니다.", this.id);
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class PostReadResponse {
        private Integer id;
        private String title;
        private String body;
        private String userName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime lastModifiedAt;

        public static PostReadResponse toPostReadResponse(Post post) {
            return PostReadResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .userName(post.getUser().getUserName())
                    .createdAt(post.getCreatedAt())
                    .lastModifiedAt(post.getLastModifiedAt())
                    .build();
        }
    }
}